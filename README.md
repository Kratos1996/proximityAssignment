# proximityAssignment
- Live City wise AQI (Air Quality Index) Table - AQI highlighted by different colors based on level (Good, Poor, Severe, etc, refer to chart on next page) -
AQI upto 2 decimal places - Clicking on the city should open a real time (30s interval or more) chart of the AQI of the selected city 
Please feel free to use/showcase your creativity. Use the shown requirements as guidelines.  
DATA Your app should subscribe for updates via WebSockets Server url: ws://city-ws.herokuapp.com/  Updates (WebSocket messages) will be provided in the following 
form: [ { city:”Mumbai”, aqi:”150.23145”}, { city:”Delhi”, aqi:”250.23145”} , . . . ] 
Each update will contain 0 or more cities/AQI pairs. You may assume that update messages will have no more than 12 cities/AQI pairs.
A GUIDE TO AQI (AIR QUALITY INDEX)

You may use this chart to use colors to give visual feedback to the output that is being displayed.
GOALS
Use a Graph library to showcase live tracking of a chosen city’s AQI at a 30 second interval
Optionally, add UI enhancements you feel the app should have, for eg. Sparklines, historical data or anything you feel helps improve data visualization or interaction
Share the code with us on a public GitHub repository and include the APK file as well.
Explain App Architecture and core logic in the repo Readme file


Do note:
You can use any libraries you want unless communicated otherwise (do consider performance implications).
The server is likely working; if you cannot connect to the WebSocket server think about why this may be and then think of solutions/workarounds. Please do document your choice.
We will be evaluating the following:
Code quality, brevity, readability, maintainability, coding standards
(Development) Time taken vs features built (mention the time you took in the Readme file)
UI quality and choice of graphing libs
