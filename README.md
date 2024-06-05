### DeeSee Comics Superhero Distribution Platform
Welcome to the DeeSee Comics Superhero Distribution Platform! This platform provides a web service for managing and retrieving information about superheroes employed by DeeSee Comics. With this platform, you can store superheroes, filter them by their superpowers, and even encrypt their true identities for added protection.

## Getting Started
To get started with the superhero distribution platform, follow these steps:

Clone the Repository: Clone this repository to your local machine using the following command:

´´`

git clone https://github.com/NehaThawani44/Encryption.git

`´´
## 1. Install Dependencies: Make sure you have Java 17 and Maven installed on your machine. Then, navigate to the project directory and install the dependencies:

`´´
cd superhero-service
mvn install
`´´
## 2. Run the Application: Start the application using Maven:

`´´
mvn spring-boot:run
`´´
## 3. Access the Web Service: Once the application is running, you can access the web service using the provided endpoints.

### Endpoints
The superhero distribution platform provides the following endpoints:

GET /api/superheroes: Retrieve all superheroes stored in the platform.
GET /api/superheroes/by-power: Retrieve superheroes that match given superpower(s).

### Query Parameters
power: (required for /by-power endpoint) Filter superheroes by the specified superpower(s). Accepts a comma-separated list of superpowers.
encrypt: (optional) Encrypt the identities of the retrieved superheroes. Set to true to enable encryption.

### Example Usage
Retrieve all superheroes:
`´´GET /api/superheroes `´´
Retrieve superheroes with specific superpowers:

`´´
GET /api/superheroes/by-power?power=strength,flight&encrypt=true
`´´
## Data Encryption
The platform uses proprietary encryption called "DeeSee Chiffre" to encrypt the true identities of superheroes. This encryption shifts each letter of the identity by a specified key, ensuring added protection for enrolled superheroes.

## Superhero JSON Structure
The JSON structure for superheroes follows this format:

{
    "name": "Superman",
    "identity": {
        "firstName": "Clark",
        "lastName": "Kent"
    },
    "birthday": "1977-04-18",
    "superpowers": ["flight", "strength", "invulnerability"]
}
