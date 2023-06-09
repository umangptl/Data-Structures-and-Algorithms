# Web Page Categorization Program
This is a web page categorization program implemented in Java. The program allows you to read and analyze multiple web pages, maintaining word frequencies for each page. It also provides a similarity metric to determine the most closely related known page to a user-entered URL.

## Requirements
To run the program, you need the following:
- Java 
- Jsoup library 
- Swing and JavaFX components for the GUI

## Features
- Reads 10 (or more) web pages from a control file and maintains word frequencies for each page.
- Utilizes TF-IDF (Term Frequency-Inverse Document Frequency) as the similarity metric, considering word frequencies and other attributes.
- Parses web pages using Jsoup to extract words and other components.
- Provides a GUI interface using Swing and JavaFX components, allowing the user to enter a URL and find the most closely related known page.
- Uses existing library collections for data structures, except for a custom hash table class implemented for maintaining word frequencies.

## File Structure
- **Main.java:** The main entry point of the program.
- **GUI.java:** Implements the graphical user interface using Swing components.
- **FreqTable.java:** Represents the frequency table for word occurrences.
- **TFIDF.java:** Calculates the TF-IDF values for words in web pages.
- **Hashtables.java:** Custom hash table class for maintaining word frequencies.
- **URLS.txt:** Control file containing the URLs of known web pages.
