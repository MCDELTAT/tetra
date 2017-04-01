# Github Repo for CSE455 Project Tetranucleotide Viewer

The goal of the project is to build a Tetranucleotide frequency viewer.
This will take the proprietary file format as provided by Dr. Dodsworth, parse it by row,
and then plot each row as a point representing a contiguous segment of the DNA sample being analyzed.
With the ability to go cross platform in mind from the outset, we decided to make use of WebGL, an open
Web standard that uses OpenGL ES 2.0 in the browser. More specifically we are using the helper library
three.js to create the graph and the points on it.

# Structure
There are two main components in this setup, 1) a very simple Android app that simply presents a splash screen
and a Android WebView to the user, and 2) the web app itself.

### Android WebView App
1) Android WebView is an embedded version of the Android web browser and so is capable of all features supported by the Android Chrome version (see caniuse.com for full list). Before building,
you will have to point the webview at a particular website. In this case, it was a simple Apache deployment on DigitalOcean.
To change the URL pointed to, simply modify line 53 of the MainActivity.java file in src/.../tetra/
To build the Android app, simply open the project in Android studio and hit Build+Run. The configuration file is set to deploy
to the latest tested version of Android Lollipop.

It is recommended to have a file browser installed on the system, preferably one without ads.
The data is a proprietary formatted CSV by Dr. Dodsworth, these are found in the DOCS/ directory of the repo.
As per the SPMP, all input files are stored locally, so save these input files on an SD card.

### Tetra Web App Graph

2) The web app itself is extremely minimalistic, in the form of an HTML document that references one large JS file
that handles all the logic of the web app. The flow of the web app is to draw the initial graph, wait until the user provides
an input file and then immediately run it through the parser (papaparse.js) and into one large global object. From there, the
app will perform some basic math on the object to get the minima and maxima, and then graph the points accordingly. The user can optionally
hide species that are within the object. In this version the hiding functionality is not abstracted and simply hides the groups of the first
three objects in the group of species. To test out the web app, simply run a local server on the folder, such as the
SimpleHTTPServer in Python, a simple express server in node.js or rack in ruby.
