# Alliander-RFID-component-registration

This is a java project for demo purposes and to test the possibility of using RFID technology in MSRs to automatize (a part of) the registration process. The method chosen to automate (a part of) the registration process is to provide every component in a MSR with a RFID tag. This tag is linked with a digital twin in the cloud. Hereby creating a digital representation of all elements of the MSRs and the component herein.

This project is done for the R&D department at Alliander by student of the HAN with the minor Smart Industry (2021-2022).

**Students:**

- Ard van Schoonhoven
- Evan Franciszok
- Giel Simons



## Installation

To use the code in this repository, java 11 (or higher) needs to be installed on the device. Visual Studio Code with the Extension Pack for Java by Microsoft (any other IDE with support for java should also be sufficient). This should be all that is required to run this project.

To open the project clone this repository locally somewhere on you computer. This can be done by downloading this repository or cloning it with GIT with a tool of choice and should also be possible with Visual Studio Code (and a lot of other IDEs). Now that the project files are on your local computer open Visual Studio Code (or you IDE of choice) and open the folder in which the cloned project files are located. In the explorer (of the IDE) go to *src/main/PrototypeApplication.java* or any other java file in the *src/main* folder. Now you should be able to run the code by pressing the run code button (above line 11 in *PrototypeApplication.java*, play button in the top right corner or via the Run tab in the top of the program). This way this can be done varies with the IDE.

If you program is running you should see a output in the terminal with the giant text "*Spring>>>*". Now the UI is accessible via the browser at ***http://localhost:8080/***.



## External soft- and hardware

In this project external soft- and hardware has been used. They have been chosen as they were the best fit for the short time span in which the project had to be executed in combination with the knowledge of the student at the time. These choices would probably not be the best fit for any other use cases with this project other than demo purposes.

Here is a list with all the soft- and hardware and a short description of the usages of it in this project:

| Software or hardware                   | Usage description                                            |
| -------------------------------------- | ------------------------------------------------------------ |
| SQLite                                 | To store all the digital twins of the components and the MSRs |
| GSON                                   | To Parse JSON response messages from the RFID reader         |
| Pepperl+Fuchs IUT-F190-B40-2V1D-FR1-01 | To scan RFID tags using UHF RFID                             |
| Maven                                  | Used to import GSON and nessesaties for Spring Boot          |
| Spring Boot                            | Handel the UI                                                |
| Thymeleaf                              | Server-side template engine for creating the dynamic web page |

​	

## Software explanation

To create a working demo of a RFID registration system, a layer pattern was used with a controller, service and persistence layer. The controller contains all the logic for the UI. The persistence layer is responsible for two different functions, the first is for communicating with the RFID reader and the second is for communicating with the database. In between these two layers lies the service layer with connects both layers together and determines and returns the required data for the UI, reader and the database.
