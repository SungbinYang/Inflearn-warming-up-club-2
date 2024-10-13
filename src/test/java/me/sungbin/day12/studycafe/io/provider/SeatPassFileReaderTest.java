package me.sungbin.day12.studycafe.io.provider;

import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

class SeatPassFileReaderTest {

    @Test
    @DisplayName("올바른 이용권 파일이 제공될 때 이용권 목록이 성공적으로 로드된다")
    void shouldLoadSeatPassesSuccessfullyWhenValidFileProvided() {
        // Given: 정상적인 파일 데이터 모의
        List<String> validFileContent = List.of(
                "HOURLY,5,10000,0.1",
                "WEEKLY,7,20000,0.15",
                "FIXED,30,50000,0.2"
        );

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv")))
                    .thenReturn(validFileContent);

            SeatPassFileReader reader = new SeatPassFileReader();

            // When: 이용권 목록을 읽음
            StudyCafeSeatPasses result = reader.getSeatPasses();

            // Then: 이용권 목록이 예상대로 로드됨
            assertThat(result.findPassBy(StudyCafePassType.HOURLY)).hasSize(1);
            assertThat(result.findPassBy(StudyCafePassType.FIXED)).hasSize(1);
        }
    }

    @Test
    @DisplayName("이용권 파일에 알 수 없는 유형의 데이터가 포함된 경우 예외가 발생한다")
    void shouldThrowExceptionWhenFileContainsInvalidPassType() {
        // Given: 잘못된 데이터 모의
        List<String> invalidFileContent = List.of("INVALID_TYPE,5,10000,0.1");

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv")))
                    .thenReturn(invalidFileContent);

            SeatPassFileReader reader = new SeatPassFileReader();

            // When & Then: 잘못된 유형의 데이터가 포함된 파일을 처리할 때 예외가 발생
            assertThatThrownBy(reader::getSeatPasses)
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("이용권 파일이 누락된 경우 예외가 발생한다")
    void shouldThrowExceptionWhenFileNotFound() {
        // Given: 파일 누락 상황 모의
        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv")))
                    .thenThrow(new IOException("파일을 찾을 수 없습니다."));

            SeatPassFileReader reader = new SeatPassFileReader();

            // When & Then: 파일이 없을 때 예외가 발생
            assertThatThrownBy(reader::getSeatPasses)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("파일을 읽는데 실패했습니다.");
        }
    }

    @Test
    @DisplayName("잘못된 형식의 이용권 데이터가 포함된 경우 예외가 발생한다")
    void shouldThrowExceptionWhenFileContainsMalformedData() {
        // Given: 잘못된 형식의 데이터 모의
        List<String> malformedFileContent = List.of("HOURLY,INVALID,10000,0.1");

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv")))
                    .thenReturn(malformedFileContent);

            SeatPassFileReader reader = new SeatPassFileReader();

            // When & Then: 형식이 맞지 않는 데이터 처리 시 예외가 발생
            assertThatThrownBy(reader::getSeatPasses)
                    .isInstanceOf(NumberFormatException.class);
        }
    }

    @Test
    @DisplayName("이용권 파일이 비어 있는 경우 빈 목록을 반환한다")
    void shouldReturnEmptyListWhenFileIsEmpty() {
        // Given: 빈 파일 데이터 모의
        List<String> emptyFileContent = List.of();

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv")))
                    .thenReturn(emptyFileContent);

            SeatPassFileReader reader = new SeatPassFileReader();

            // When: 빈 파일에서 이용권 목록을 로드
            StudyCafeSeatPasses result = reader.getSeatPasses();

            // Then: 빈 목록이어야 함
            assertThat(result.findPassBy(StudyCafePassType.HOURLY)).isEmpty();
        }
    }
}
