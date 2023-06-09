# Web Scraping and Categorization
This Java code is an example of web scraping and categorization of web pages based on a predefined set of categories. It uses Jsoup library for web scraping and performs TF-IDF (Term Frequency-Inverse Document Frequency) calculations to determine the category of a given web page.

## Dependencies
This code depends on the following libraries:
- Jsoup: A Java library for parsing HTML and XML documents.
Make sure you have these libraries added to your project's classpath before running the code.

## Code Structure
### Category enum
- Represents the categories used for web page categorization.
- Each category has an associated Wikipedia URL.
- The enum also contains methods for updating the category data and loading it into a custom B-tree data structure.

### CustomUrl class
- Represents a custom URL object with a frequency table for words found in the web page.

### FreqTable class
- Extends a custom hash table to store word frequencies for a web page.
- Calculates term frequency (TF), inverse document frequency (IDF), and TF-IDF values for words in the web page.

### GUI class
- Represents a graphical user interface (GUI) using Swing components.
- Provides a text field for entering a URL and a button for submitting the URL.
- Displays the category of the web page and a list of similar links.

### Main_util class
- Contains the main logic for web page categorization and finding similar links.
- Extracts words from the given URL and calculates the TF-IDF ratio for each category.
- Determines the most similar category and finds similar links based on the TF-IDF values.

### Scrap class
- Represents a collection of CustomUrl objects.
- Provides a method to calculate the total number of documents containing a specific term.

### Webpage class
- Provides a static method to retrieve the body text of a web page using Jsoup library.

## Running the Code
To use this code, follow these steps:

1. Make sure you have the required dependencies (e.g., Jsoup) added to your project.
2. Create an instance of the GUI class to display the GUI window.
3. Enter a Wikipedia URL in the text field and click the "Submit" button.
4. The code will scrape the web page, categorize it, and display the category along with a list of similar links.
Note: This code is a simplified example and may not work with all web pages. It assumes that the input URLs are valid Wikipedia pages.

## Further Improvements
- Add error handling and input validation to handle invalid URLs or connection failures.
- Implement a more robust and accurate web page categorization algorithm.
- Optimize the code for performance and memory usage.
- Add additional features such as filtering out common words or providing more detailed category information.
