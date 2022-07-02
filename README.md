
# <img src="https://user-images.githubusercontent.com/46292847/176991937-7f4d6e0e-5e6e-4f9e-a36f-63418af81fa1.png" width="30" height="30" /> Swift Download Manager

![GitHub](https://img.shields.io/github/license/matink112/SwiftDownloadManager?style=for-the-badge) ![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/matink112/SwiftDownloadManager?include_prereleases&style=for-the-badge) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/matink112/SwiftDownloadManager?style=for-the-badge)

SDM is a download manager built on the look of XDM and IDM written in Java.

The application splits download file into multiple blocks. Attempts to download each block parallelly which improves the download speed.

![output](https://user-images.githubusercontent.com/46292847/176756279-dd34f12a-e3d3-4245-8bc7-5dea48dab567.gif)

# Run with gradle

If you do not have gradle click [here](https://gradle.org/install/) to get the latest version of it.

Clone the repository into your local system

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

