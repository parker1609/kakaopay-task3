package com.parksungbum.kakaopaytask3.domain.fund;

import com.parksungbum.kakaopaytask3.domain.housingfinance.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Long> {

    List<Fund> findALLByHousingFinanceTime_Year(Year year);

    List<Fund> findAllByHousingFinanceTime_YearAndInstitution_Name(Year year, String bankName);

    @Query(value =
            "SELECT detail_total_amount_table.year, detail_total_amount_table.name FROM (\n" +
                    "SELECT housing_finance_time.year, institution.name, SUM(fund.amount) AS detail_amount FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance_time ON fund.housing_finance_time_id = housing_finance_time.id\n" +
                    "GROUP BY institution.name, housing_finance_time.year\n" +
                    "ORDER BY housing_finance_time.year) detail_total_amount_table\n" +
                    "ORDER BY detail_amount DESC\n" +
                    "LIMIT 1",
            nativeQuery = true)
    List<Object[]> findYearAndInstitutionOfMaxFund();

    @Query(value =
            "SELECT annual_average_amount_table.year, annual_average_amount_table.annual_avg FROM (\n" +
                    "SELECT housing_finance_time.year, institution.name, ROUND(SUM(fund.amount) / 12) AS annual_avg FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance_time ON fund.housing_finance_time_id = housing_finance_time.id\n" +
                    "WHERE institution.name = ?1\n" +
                    "GROUP BY institution.name, housing_finance_time.year) annual_average_amount_table\n" +
                    "ORDER BY annual_avg DESC\n" +
                    "LIMIT 1",
            nativeQuery = true)
    List<Object[]> findMaxAverageAmountByBank(String bankName);

    @Query(value =
            "SELECT annual_average_amount_table.year, annual_average_amount_table.annual_avg FROM (\n" +
                    "SELECT housing_finance_time.year, institution.name, ROUND(SUM(fund.amount) / 12) AS annual_avg FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance_time ON fund.housing_finance_time_id = housing_finance_time.id\n" +
                    "WHERE institution.name = ?1\n" +
                    "GROUP BY institution.name, housing_finance_time.year) annual_average_amount_table\n" +
                    "ORDER BY annual_avg ASC\n" +
                    "LIMIT 1",
            nativeQuery = true)
    List<Object[]> findMinAverageAmountByBank(String bankName);

    @Query(value =
            "SELECT housing_finance_time.year, housing_finance_time.month, fund.amount FROM fund\n" +
                    "INNER JOIN institution ON institution.id = fund.institution_id\n" +
                    "INNER JOIN housing_finance_time ON fund.housing_finance_time_id = housing_finance_time.id\n" +
                    "WHERE institution.name = ?1\n" +
                    "GROUP BY housing_finance_time.year, housing_finance_time.month, fund.amount",
            nativeQuery = true)
    List<Object[]> findYearAndMonthAndAmountByBank(String bankName);
}
