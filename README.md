# Random Chatting Platform

**Done by:**

- **Mohamed Zouaghi : https://github.com/zouaghista**
- **Addala Raed [ ME ] : https://github.com/RaedAddala**

**Inspired By: Omegle.**

1. [**General Description.**](#general_description)
1. [**How To Use It.**](#how_to_use_it)
1. [**General Documentation.**](#general_documentation)

<br>
<br>
<br>

## 1.  **General Description:**

This project is a chatting platform that utilizes a random matching system to connect clients and manages their interactions, including the pairing of two clients and handling various events related to these functionalities. 

The architecture of the project is **Event-Driven**, which means that the logic is primarily handled by an Event Emitter (**Manager** class) responsible for sending messages, an Event Listener (**ConversationListener** class) responsible for receiving notifications and messages, and supporting classes such as **Logger** and **QueueManager** for server connection and client pairing.

The client program features a graphical user interface (GUI) consisting of two windows.

**Technologies Used: *Java RMI*, *Java Swing* and *Java Threads*.**


## 2. **How to Use It:**

This Project uses Java RMI technology so in order to use it first you have to:
1. **Have Java Installed and bin file added to the PATH Variable**
    ```
    # Test it using this
    java -version
    ```

1. **Start the RMI Registry:**
    ```
    # this should be done on its own terminal instance
    # In Windows:
    start rmiregistry
    # In Linux and Unix-based OS:
    rmiregistry &
    ```
1. **Run the Server:**
    ```
    # Go To binaries/Server folder and run this command:
    java ChatServer
    ```
1. **Run the Clients ( as many as you need to: )**
    ```
    # Go To binaries/Client folder and run this command:
    java ChatClient
    ```

<br>
<br>

## 3. **General Documentation:**
### **QueueManager :**

The QueueManager is a crucial component of the server **responsible for managing waiting queues and matching clients with each other**.

It runs on its own thread to handle concurrent operations efficiently.

The QueueManager effectively handles scenarios where a client exits a room or requests to be matched with another client. 

To ensure **a single instance is used throughout the server's lifecycle**, the QueueManager follows the **Singleton pattern**.

### **ITextReceiver : *INTERFACE* :**

The ITextReceiver interface establishes the requirements for classes responsible for **receiving messages**, **handling connections**, **matching** **and** **partner quitting notifications**.

### **ILogger: *INTERFACE* :**

Ensures that the class must handle **logging to the chat server.**

### **ITexter : *INTERFACE* :**

Ensures that the class must handle **entering** and **exiting a chat**, **sending messages** and **quitting the server.**

### **ChatServer:**

The ChatServer component includes a **graphical user interface** (**GUI**) that provides a visual representation of the current pairings using the **RoomTable** class.

It creates an **instance** of the **Logger** class to initiate the client matching process.

### **ChatClient [ GUI ]:**

The ChatClient is the client-side component that features a user-friendly GUI. 

The GUI consists of a **WelcomePanel**, responsible for initializing the listener and emitter, and a **ChatBox**, which displays sent and received messages.

### **ConversationListener ( ITextReceiver ) :**

The ConversationListener implements the ITextReceiver interface. 

It handles **notifications regarding entering or quitting the queue** and **receiving messages**. 

It contains an **instance** of **ITexter** responsible for sending messages and **a reference to the GUI instance** for displaying notifications and received messages.

### **Logger ( ILogger ):**
Ensures Connection between two clients.

Uses a **Manager** and receives an **ITextReceiver** instance reference as a listener which is then published within the manager and bound to the name registry.

### **Manager ( ITexter ):**

The Manager class is a critical component that implements the ITexter interface.

*Contains two classes’ instances:*

- **ConversationManager**
- **QueueManager (**shared by all **Manager** instances**)**
- Uses the ConversationManager instance to send a message and handle its exceptions.
- Adds a client to chat queue and pair it with another client using the **QueueManager.**
- Handles quitting the chat/app event. 

### **RoomTable [ GUI ]:** 

The RoomTable component provides a GUI display on the server, showcasing all client connections and pairings in real-time.

###  **OnDisplayListener [ GUI-related Event Handler ]:**

The OnDisplayListener is responsible for handling window-related events in the GUI. 

It ensures a smooth quitting process without crashing the server or causing excessive blocking time for other clients.

Also, it ensures **joining** **a queue** first time the window is created.

###  **ConversationManager:**
 Responsible of handling all messaging operations within the chat platform.

Has **another ConversationManager instance reference** to match to and chat with.

The ConversationManager listens for incoming messages through an **ITextReceiver**.

Has a static variable **count,** used to generate unique IDs.


<br>
<br>

## Contributing and Feedback

I am a Student and I am still in the process of learning. So, I appreciate your interest in contributing to this project and value your feedback. If you have any suggestions, improvements, or notice any areas that could be enhanced, please feel free to let me know. Your input is highly valuable to me.

Here are some ways you can contribute and provide feedback:

- **Report issues**: If you come across any issues or bugs, please [submit an issue](link-to-issue-tracker) with a detailed description. Include steps to reproduce the problem, expected behavior, and any relevant screenshots.

- **Feature requests**: If you have any ideas for new features or improvements, [open an issue](link-to-issue-tracker) and describe your suggestion. Provide as much detail as possible, including use cases and potential implementation strategies.

- **General feedback**: If you have any other feedback, questions, or comments, please [reach out](contact-information) to me. I am open to discussions and happy to address any concerns.

### Thank you for taking the time to contribute and provide feedback
