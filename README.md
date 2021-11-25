# Beverage Machine Spring Boot app

## How to install

### Using Git

1.  Clone the project from github. Change "myproject" to your project name.

```bash
https://github.com/Andrii-25/beverage-machine.git ./myproject
```

### Using manual download ZIP

1.  Download repository
2.  Uncompress to your desired directory
 
## Steps to run
1. Build the project using `mvn clean install`
2. Run using `java -jar target/beveragemachine-0.0.1-SNAPSHOT.jar`
3. The web application is accessible via localhost:8080

## Alternatively, you can run the app without packaging it using:
`mvn spring-boot:run`

## Routes

### POST Routes

- /machine/init - to init beverage machine.
- /machine/money?banknote=[banknote]&coin=[coin] (banknotes: ONE, TWO, FIVE, TEN, TWENTY; coins: TWENTY_FIVE, FIFTY) - to insert money into the machine.
- /machine/:product - to choose product.
- /machine/reset - to reset machine.
- /machine/refund - to get refund.

### GET Routes

- /machine/product - to get the product from the machine.
- /machine/stats - to get stats of the machine.
