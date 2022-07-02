
# Swift Download Manager
SDM is a download manager built on the look of XDM and IDM that written in Java.

The application splits download file into multiple blocks. Attempts to download each block parallelly which improves the download speed.

![output](https://user-images.githubusercontent.com/46292847/176756279-dd34f12a-e3d3-4245-8bc7-5dea48dab567.gif)

# Run with gradle

If you do not have gradle go [here](https://gradle.org/install/) to get the latest version of gradle.

Clone the repository in your local system

`git clone https://github.com/matink112/SwiftDownloadManager.git`

To build the project go to project root and run the following command.

`cd SwiftDownloadManager`

`gradle run`


# Build and Run

`gradle jar`

A fat jar will be built in `app/builds/libs` directory.

Execute the jar file by the following command

`java -jar <filename.jar>`

## License
[MIT](https://choosealicense.com/licenses/mit/)

