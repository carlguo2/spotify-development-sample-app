# spotify-development-sample-app
Uses the Spotify Android SDK (https://github.com/spotify/android-sdk) and the Web API (https://developer.spotify.com/documentation/web-api/)
to retrieve user's last (1-50) most recently listened to tracks. The tracks requested are returned in a JSON format which is parsed using
Google GSON library. AnyChart Android library is used to sort that data into a pie chart.

# How to use
After Logging in, add a number from 1-50 in the EditText field. If this is not done, the button will be disabled. After adding into the
EditText, press the 'Graph Data' button (may need to press twice.) You can update the graph by adding another number into the EditText
and press the button again (again will need to press twice here.)
