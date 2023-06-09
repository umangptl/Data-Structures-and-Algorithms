# Web Page Similarity Analysis
This program is designed to collect Wikipedia pages and analyze their connectivity using graph theory. It allows users to calculate similarity metrics between pages and find the shortest path between two selected pages.

## Requirements
The program fulfills the following requirements:

- Collect at least 500 Wikipedia pages and links from these pages to other Wikipedia pages.
- Calculate similarity metrics based on the collected data.
- Perform a connectivity check and report the number of disjoint sets or spanning trees from arbitrary roots.
- Store the collected data persistently, possibly in a serialized file.
- Provide a GUI or web-based interface for user interaction.
- Allow users to select any two pages by title and display the shortest weighted path between them, considering the inverse similarity metric. Optionally indicate whether the path passes through a medoid.

## Usage
To use the program, follow these steps:

1. Run the program by executing the main class, GUI, which provides a graphical user interface.
2. The interface will prompt you to enter two Wikipedia URLs. Make sure to enter valid Wikipedia URLs.
3. After entering the URLs, click the "Search" button to calculate the shortest weighted path between the two pages.
4. The program will display the path graphically and indicate whether it passes through a medoid.
5. You can also view the list of disjoint sets and the number of disjoints calculated from the collected data.

## Dependencies
The program relies on the following dependencies:

- Jsoup: A Java library for parsing HTML and extracting data from web pages.
- Java Swing: A Java GUI widget toolkit for creating graphical user interfaces.
- AWT (Abstract Window Toolkit): A Java GUI library for creating and managing windows, buttons, labels, and other UI components.

## Data Collection and Graph Analysis
The program uses web scraping techniques to collect Wikipedia pages and extract links between them. It applies Dijkstra's algorithm to calculate the shortest weighted path between two selected pages, considering the inverse similarity metric as the weight. It also performs a connectivity check and generates a list of disjoint sets or spanning trees.

## Limitations
Please note the following limitations of the program:

- The program collects a maximum of 500 Wikipedia pages and their links due to the specified requirement.
- The program relies on the availability and accessibility of Wikipedia pages during the data collection process.
- The accuracy of the similarity metrics and shortest path calculations depends on the quality and completeness of the collected data.
- Feel free to modify the code and adapt it to your specific needs or extend it with additional features.
