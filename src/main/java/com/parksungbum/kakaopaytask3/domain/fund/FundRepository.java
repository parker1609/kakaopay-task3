package com.parksungbum.kakaopaytask3.domain.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Long> {

    @Query(value =
            "SELECT housing_finance.year, SUM(fund.amount) AS total_amount FROM fund\n" +
                    "INNER JOIN housing_finance ON fund.housing_finance_id = housing_finance.id\n" +
                    "GROUP BY housing_finance.year",
            nativeQuery = true)
    List<Object[]> findAnnualTotalFund();

    @Query(value =
            "SELECT housing_finance.year, institution.name, SUM(fund.amount) AS detail_amount FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance ON fund.housing_finance_id = housing_finance.id\n" +
                    "GROUP BY institution.name, housing_finance.year\n" +
                    "ORDER BY housing_finance.year",
            nativeQuery = true)
    List<Object[]> findAnnualInstitutionFund();

    @Query(value =
            "SELECT detail_total_amount_table.year, detail_total_amount_table.name FROM (\n" +
                    "SELECT housing_finance.year, institution.name, SUM(fund.amount) AS detail_amount FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance ON fund.housing_finance_id = housing_finance.id\n" +
                    "GROUP BY institution.name, housing_finance.year\n" +
                    "ORDER BY housing_finance.year) detail_total_amount_table\n" +
                    "ORDER BY detail_amount DESC\n" +
                    "LIMIT 1",
            nativeQuery = true)
    List<Object[]> findYearAndInstitutionOfMaxFund();
}
