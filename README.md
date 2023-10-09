# demo-location-api-bff
Location API Backend For Frontend

This sample application will determine the distance in KM to 2 decimal places of two UK postcodes.

e.g http://localhost:8080/distance/calculator?postcode1=W1A%201AA&postcode2=E1%201AB
will return

```json
{
   "km": 5.37
}
```

## Running the app locally

```
 ./gradlew bootRun
```

## Still to do:

* Make calls concurrent to get the postcode information
* Write the correct validation for postcode format
* Move the validation for postcode into a spring validator
* Move sample data into files
* Cache requests to provide state management example 