package org.message.producer.util;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomUtils;
import org.message.model.Address;
import org.message.model.Comment;
import org.message.model.Report;
import org.message.model.User;
import org.message.model.util.ReportStatus;

import java.util.*;

@UtilityClass
public class RandomUtil {

    public static User generateUser(UUID testUUID) {
        UUID userUUID = UUID.randomUUID();
        faker = getRandomFaker();
        Address address = generateAddress(userUUID);
        List<Report> reports = generateReports(userUUID, testUUID);

        String idNumber = faker.idNumber().valid();
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String cellPhone = faker.phoneNumber().cellPhone();
        return User.builder()
                .uuid(userUUID)
                .testUUID(testUUID)
                .idNumber(idNumber)
                .name(name)
                .lastName(lastName)
                .email(email)
                .cellPhone(cellPhone)
                .address(address)
                .reports(reports)
                .build();
    }

    private static final Map<Integer, Locale> availableLocale = Map.of(
            0, Locale.ENGLISH,
            1, Locale.US,
            2, Locale.FRANCE,
            3, Locale.GERMANY,
            4, Locale.ITALY
    );

    private static Faker faker;

    private static Faker getRandomFaker() {
        int randomLocaleId = RandomUtils.nextInt(0, 5);
        Locale locale = availableLocale.get(randomLocaleId);
        return new Faker(locale);
    }

    private static Address generateAddress(UUID userUUID) {
        UUID addressUUID = UUID.randomUUID();
        String streetName = faker.address().streetName();
        String number = faker.address().buildingNumber();
        String city = faker.address().city();
        String country = faker.address().country();
        return Address.builder()
                .uuid(addressUUID)
                .userUuid(userUUID)
                .streetName(streetName)
                .number(number)
                .city(city)
                .country(country)
                .build();
    }

    private static List<Report> generateReports(UUID userUUID, UUID testUUID) {
        int numberOfReports = RandomUtils.nextInt(1, 10);
        List<Report> reports = new ArrayList<>();

        do {
            reports.add(generateReport(userUUID, testUUID));
            numberOfReports--;
        } while (numberOfReports > 0);

        return reports;
    }

    private static Report generateReport(UUID userUUID, UUID testUUID) {
        UUID reportUUID = UUID.randomUUID();
        int summaryWords = RandomUtils.nextInt(1, 3);
        int sentenceCount = RandomUtils.nextInt(3, 6);
        return Report.builder()
                .uuid(reportUUID)
                .testUUID(testUUID)
                .userUuid(userUUID)
                .summary(faker.lorem().sentence(summaryWords))
                .description(faker.lorem().paragraph(sentenceCount))
                .status(ReportStatus.randomReportStatus())
                .comments(getComments(reportUUID))
                .build();
    }

    private static List<Comment> getComments(UUID reportUUID) {
        int commentsCount = RandomUtils.nextInt(6, 18);
        List<Comment> comments = new ArrayList<>();
        do {
            comments.add(getComment(reportUUID));
            commentsCount--;
        } while (commentsCount > 0);
        return comments;
    }

    private static Comment getComment(UUID reportUUID) {
        return Comment.builder()
                .uuid(UUID.randomUUID())
                .reportUuid(reportUUID)
                .description(faker.lorem().paragraph())
                .build();
    }
}
