# FMRAppV2

Android Application developped by Shawne Comeau, Cassie Doiron and Jeremy Wasson for CS2063

The app represents a tool that allows students to create reports for issues on campus and submit them to Facilities Management to be resolved.

Expected User Flow
  - App opens on home page with option to click on 'Create a Report' or 'View Reports' buttons
  - Clicking on 'View Reports' takes the user to the feed page where the reports are displayed
  - Clicking on 'Create a Report' takes the user to the sign in page
  - The user can sign in using a credential that is pre stored in the database (Email = 'jsmith1@unb.ca' Password = 'password') and then click the 'Submit' button
  - After a succcessful sign in the user is take to the report page where they need to input a title, building/location, description and picture for the report
  - Once all the fields have been populated the user can click the 'Submit Report' button
  - After filling out all of the fields and clicking the 'Submit Report' button the user will be taken to the feed page where all of the reports will show and can be scrolled through.

Completed Features:
  - Sign in page that authenticates user logins through SQLite Database (Username = 'jsmith1@unb.ca' Password = 'password')
  - Drop down menu that recommends auto completed building and location names on the report page
  - Ability to create a report about an issue at UNB and add it to a SQLite Database
  - Ability to add a picture to a report and store it with the report in an SQLite Database
  - Feed page that retrieves and displays all user creates reports and can be scrolled through
  
  Supported API Levels
    - API Level 33 on Google Nexus 4 phone 
    - API Level 31 (Android 12) on Google Pixel 4a 
    - API Level 30 on Google Pixel 5 phone 
   
  Test Cases 
    - Press “View Reports” Button (should bring you to feed page without having to sign in) 
    - Press the back arrow (should take you back to the home page) 
    - Press “Submit Report” (should bring you to sign in page) 
    - Attempt to sign in without inputting any credentials into the text fields (should fail and display proper toast message) 
    - Attempt to sign in with improper credentials (should fail and display appropriate toast message) 
    - Attempt to sign in with using ‘jsmith1@unb.ca’ as username and ‘password’ as the password (should succeed, display appropriate toast message, and     move you to the report page) 
    - Attempt to submit a report with one of the text fields blank (should fail and display appropriate toast message) 
    - Attempt to submit a report without a photo (should fail and display appropriate toast message) 
    - Attempt to submit a report with all text fields populated and picture taken (should succeed, display appropriate toast message and move you to the feed page with the new report added) 
  - Attempt to scroll through feed (should succeed if) 
  - Press back arrow (Should bring you back to report page with all fields cleared) 
