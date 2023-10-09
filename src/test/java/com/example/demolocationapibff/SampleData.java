package com.example.demolocationapibff;

public class SampleData {

    public static String postcode1() {
        return """
                {
                  "status": 200,
                  "result": {
                    "postcode": "W1A 1AA",
                    "quality": 1,
                    "eastings": 528887,
                    "northings": 181593,
                    "country": "England",
                    "nhs_ha": "London",
                    "longitude": -0.143799,
                    "latitude": 51.518561,
                    "european_electoral_region": "London",
                    "primary_care_trust": "Westminster",
                    "region": "London",
                    "lsoa": "Westminster 013D",
                    "msoa": "Westminster 013",
                    "incode": "1AA",
                    "outcode": "W1A",
                    "parliamentary_constituency": "Cities of London and Westminster",
                    "admin_district": "Westminster",
                    "parish": "Westminster, unparished area",
                    "admin_county": null,
                    "date_of_introduction": "199002",
                    "admin_ward": "West End",
                    "ced": null,
                    "ccg": "NHS North West London",
                    "nuts": "Westminster",
                    "pfa": "Metropolitan Police",
                    "codes": {
                      "admin_district": "E09000033",
                      "admin_county": "E99999999",
                      "admin_ward": "E05013808",
                      "parish": "E43000236",
                      "parliamentary_constituency": "E14000639",
                      "ccg": "E38000256",
                      "ccg_id": "W2U3Z",
                      "ced": "E99999999",
                      "nuts": "TLI32",
                      "lsoa": "E01004765",
                      "msoa": "E02000972",
                      "lau2": "E09000033",
                      "pfa": "E23000001"
                    }
                  }
                }
                                """;
    }

    public static String postcode2() {
        return """
                {
                "status": 200,
                "result": {
                "postcode": "E1 1AB",
                "quality": 1,
                "eastings": 534253,
                "northings": 181031,
                "country": "England",
                "nhs_ha": "London",
                "longitude": -0.066722,
                "latitude": 51.512262,
                "european_electoral_region": "London",
                "primary_care_trust": "Tower Hamlets",
                "region": "London",
                "lsoa": "Tower Hamlets 021F",
                "msoa": "Tower Hamlets 021",
                "incode": "1AB",
                "outcode": "E1",
                "parliamentary_constituency": "Bethnal Green and Bow",
                "admin_district": "Tower Hamlets",
                "parish": "Tower Hamlets, unparished area",
                "admin_county": null,
                "date_of_introduction": "200806",
                "admin_ward": "Whitechapel",
                "ced": null,
                "ccg": "NHS North East London",
                "nuts": "Tower Hamlets",
                "pfa": "Metropolitan Police",
                "codes": {
                "admin_district": "E09000030",
                "admin_county": "E99999999",
                "admin_ward": "E05009336",
                "parish": "E43000220",
                "parliamentary_constituency": "E14000555",
                "ccg": "E38000255",
                "ccg_id": "A3A8R",
                "ced": "E99999999",
                "nuts": "TLI42",
                "lsoa": "E01032767",
                "msoa": "E02000884",
                "lau2": "E09000030",
                "pfa": "E23000001"
                }
                }
                }
                """;
    }
}
