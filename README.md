# Geddit

Reddit is a set of subreddits, each subreddit being a thread containing several discussions associated to a single topic. **Geddit** is like reddit but with subreddits associated to places instead of topics. Imagine you are walking in the street heading for your favorite restaurant. You launch Geddit to find all the surrounding subreddits, you see there is no subreddit associated to your restaurant yet. You can create one, anyone around can see and join it ! Anyone using the app can create discussions about their favorite dish or their best story related to this place, they can post links to photos, videos and upvote or downvote the different discussions. 

# Technical aspect

## Web server

The web server part has been developped from scratch in PHP to provide an API. It can give back the data to the client, create a subgeddit, create threads on a subgeddit, create replies to a thread, up/down vote threads and individual comments.

The thread names are automatically extracted from OpenStreetMap using the Nominatim API.

A web view of the backend is available to poll the API manually.

## App

The app automatically refresh the data from the API and displays the subgeddits either as a list with a distance to each location or as a map with markers for each subgeddit. It offers all the functionnalities matching the API's capabilities: reading threads for any subgeddit, up/down vote them, read comments in a thread and individually up/down vote them.

# The team

Please all praise Fred, Greg and Matteo for this awesome service!

# Screenshots

## Main activity of the application

Shows all the sub-geddits around the user

![](screenshots/main.png)

## Map view

Map visualization of the same data

![](screenshots/map.png)

## Threads for a given location

Shows all the conversations for the given subgeddit (~place)

![](screenshots/subgeddit.png)

## Thread view

Shows the full thread

![](screenshots/topic.png)
