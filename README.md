# SearchFight

SearchFight is a Java-based command-line application designed to compare the number of search results returned by different search engines for given search terms.

## Features

- Supports multiple search engines (e.g., Google, Bing)
- Compares search result counts for provided terms
- Identifies the winner per search engine and the overall winner
- Command-line interface for easy interaction

## Prerequisites

- Java 8 or higher
- Maven 3.x

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/rajitkhosla85/searchfight.git
2. Navigate to the project directory:
   ```bash
   cd searchfight
3. Build the project using Maven:
   ```bash
     mvn clean install

## Usage
Run the application with search terms as arguments:
java -jar target/searchfight.jar "java" "python" "ruby"

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

Note: Ensure that you have the necessary API keys for the search engines you intend to use. Replace placeholders in the config.properties file with your actual API credentials.
