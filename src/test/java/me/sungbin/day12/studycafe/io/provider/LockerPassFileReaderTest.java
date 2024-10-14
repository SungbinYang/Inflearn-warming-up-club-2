package me.sungbin.day12.studycafe.io.provider;

import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

class LockerPassFileReaderTest {

    @Test
    @DisplayName("CSV 파일에서 사물함 이용권 정보를 정상적으로 로드한다")
    void shouldLoadLockerPassesSuccessfully() {
        // Given: 정상적인 파일 내용 모의
        List<String> mockFileContent = List.of(
                "FIXED,30,50000",
                "WEEKLY,7,20000"
        );

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv")))
                    .thenReturn(mockFileContent);

            LockerPassFileReader reader = new LockerPassFileReader();

            // When: getLockerPasses 호출
            StudyCafeLockerPasses result = reader.getLockerPasses();

            // Then: 이용권 정보가 정상적으로 로드되었는지 검증
            assertThat(result.findLockerPassBy(
                    StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2))
            ).isPresent();
        }
    }

    @Test
    @DisplayName("락커 이용권 파일이 누락된 경우 시스템이 파일 읽기를 실패하고 예외를 발생시킨다")
    void shouldThrowExceptionWhenFileNotFound() {
        // Given: IOException 발생을 모의
        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv")))
                    .thenThrow(new IOException("파일을 찾을 수 없습니다."));

            LockerPassFileReader reader = new LockerPassFileReader();

            // When & Then: RuntimeException 발생 검증
            assertThatThrownBy(reader::getLockerPasses)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("파일을 읽는데 실패했습니다.");
        }
    }

    @Test
    @DisplayName("락커 이용권 파일에 알 수 없는 이용권 유형이 포함된 경우 처리하지 않고 예외가 발생한다")
    void shouldThrowExceptionWhenFileContainsInvalidData() {
        // Given: 잘못된 데이터 모의
        List<String> invalidFileContent = List.of("INVALID_TYPE,30,50000");

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv")))
                    .thenReturn(invalidFileContent);

            LockerPassFileReader reader = new LockerPassFileReader();

            // When & Then: 잘못된 형식의 데이터 예외 검증
            assertThatThrownBy(reader::getLockerPasses)
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("빈 파일을 읽을 때 빈 사물함 이용권 목록을 반환한다")
    void shouldReturnEmptyLockerPassesWhenFileIsEmpty() {
        // Given: 빈 파일 내용 모의
        List<String> emptyFileContent = List.of();

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv")))
                    .thenReturn(emptyFileContent);

            LockerPassFileReader reader = new LockerPassFileReader();

            // When: getLockerPasses 호출
            StudyCafeLockerPasses result = reader.getLockerPasses();

            // Then: 빈 목록이어야 함
            assertThat(result.findLockerPassBy(
                    StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2))
            ).isNotPresent();
        }
    }
}
