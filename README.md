Overview
Hey! Welcome to my coding exercise repo. In this project, I tackled a fun challenge: automating a website to handle cookie consent and list products based on different filters. I used a data-driven approach, which made it pretty interesting!

Environment Setup
Here’s the setup I used for this exercise:
•	IDE: IntelliJ IDEA
•	Frameworks: Selenium, TestNG
•	Build Tool: Maven
•	Reporting: ExtentReports
•	Data Management: Data-driven tests powered by Excel and TestNG's DataProvider
•	Design Pattern: Page Object Model (POM)

Exercise Steps
1.	Navigate to the URL: The script starts by launching the browser and opening the given URL.
2.	Handle Cookie Consent: Implemented logic to handle the cookie consent pop-up so the tests can proceed smoothly.
3.	Click on "Parfum": The script clicks on the "Parfum" section to proceed to the products page.
4.	List Products Based on Filters: It executes data-driven tests using the given set of criteria:
   
      Criteria/Highlights	Marke	Produktart	Geschenk fu	Fur Wen
      Sale	               ?	      ?	         -	         ?
      Neu	                 -	      ?	         -	         ?
      Limitiert	           ?	      ?	         ?	         ?

      ?  - Means any value could be plugged in         -  - Means criteria is not applicable

Best Practices Incorporated
•	Page Object Model (POM): I used this to keep the code clean, maintainable, and scalable. Each web page is represented by a class, making the test scripts more organized.
•	Modular Code: I organized the code into reusable classes and methods, making it easy to manage.
•	Cross-Browser Testing: I leveraged WebDriverManager to randomly execute tests across multiple browsers (like Chrome and Edge), ensuring compatibility.
•	Explicit Waits: Used this to handle synchronization issues and ensure elements are available before interactions, reducing flaky test failures.
•	Data-Driven Testing: Applied Boundary Value Analysis (BVA), Equivalence Partitioning (EP), and random selection logic. With TestNG’s DataProvider, I used Excel sheets to manage the data inputs, which improved coverage and flexibility.
•	Logging and Reporting: Used ExtentReports for detailed logging, including the status of each test and assertion, making it easy to track issues.

Code Optimizations
•	ThreadLocal WebDriver Management: Implemented a WebDriver manager to handle multiple browser instances efficiently.
•	Custom Assertions: I created reusable assertions to ensure consistent validation across tests and improve the clarity of the reports.
•	Error Handling: Implemented error handling mechanisms to effectively catch and report exceptions, making it easier to troubleshoot issues.

Execution
•	Browser Compatibility:  Executed tests across multiple browsers (e.g., Chrome, Edge) using WebDriverManager for random browser selection.
•	Command for Execution: 'mvn clean test' OR 'mvn clean test -Dtest=ParfumPageTest'

Reporting Mechanism
ExtentReports: For its flexibility and comprehensive reporting capabilities. The reports include:
•	Test case execution status
•	Each assertions’ status along with the condition tested
•	Additional logs to help with debugging

Possible Future Enhancements
•	Enhancing the automation reports by adding more detailed insights and summary.
•	Automatically capture screenshots when a test fails and attach them to the reports for better debugging.
•	Extend the framework to include API testing for more comprehensive, end-to-end validation.

Conclusion
This project has been a great way to apply best practices in test automation. The framework is designed to be scalable and adaptable, so it’s easy to add new tests or tweak existing ones. Plus, it tackles real-world challenges like dynamic content, filters and cookies consent, making it practical for e-commerce applications.

Thanks for checking out my work! :) 
