# Data Structures & Algorithms (DSA) Simulation Suite

A comprehensive Java Swing GUI application that visually demonstrates and simulates foundational Data Structures and Algorithms (DSA). This suite maps theoretical textbook algorithms into interactive, visual workflows, helping students and developers understand how memory layout, array shifting, pointer manipulation, and boundaries (Overflow/Underflow) work in practice.

## 🚀 Key Technical Highlights
* **Graphical User Interfaces:** Built completely using Java Swing Components (`JFrame`, `JPanel`, `JTable`, `JComboBox`, custom panels with background images).
* **Algorithmic Adaptations:** Textbook standard algorithms adapted directly into real-time visual logic.
* **Robust File Handling:** Implementations feature `.csv` data parsing (Import/Export) along with dynamic asset management (image uploading and folder routing).

---

## 📂 Modules & Data Structures

### 1. Array Manipulator & Sports Equipment Inventory (HW01)
Simulates standard static array behavior with index management[cite: 4]. Data fields are split into parallel array structures (`names[]`, `categories[]`, `prices[]`) constrained by an upper bound ($UB = 100$).

* **Core Features:** 
  * **Import/Export:** Parses a 4-column CSV file (`id`, `name`, `category`, `price`) and pairs items with custom image assets dynamically.
  * **Insert:** Implements standard array element shifting ($LA[J+1] = LA[J]$). Features an auto-incrementing default ID to protect against duplicates and maintain proper sequencing.
  * **Delete:** Performs leftward structural shifts on deletion and checks for array boundary limits. Allows targeted wiping by ID, Name (Case-Insensitive), Category, or Price Range.
  * **Search & Sort:** Supports multi-tier sorting (mimicking SQL `ORDER BY Name DESC, Price DESC`) using optimized index mapping arrays paired with custom Bubble Sort logic.

### 2. Reverse Number Stack Simulator (HW02)
Visualizes **LIFO (Last In, First Out)** workflows using a dynamic, multi-stack interface (Stack A, Stack B, and Stack C).

* **Core Features:**
  * **Configurable Sizing:** Users define a universal runtime constraint length for all instances before operations begin.
  * **Strict Data Constraints:** Enforces specific algorithmic ordering laws (e.g., preventing elements greater than the current `TOP` node from being successfully pushed).
  * **Visual Guardrails:** Features dialog warnings explicitly showcasing real-time `Overflow` ($TOP == MAXSTK$) and `Underflow` ($TOP == -1$) states.

### 3. Circular Queue Luggage Simulation (HW03)
Simulates **FIFO (First In, First Out)** mechanics over a circular buffer layout mapping an airport baggage claim carousel.

* **Core Features:**
  * **State Monitor Panel:** Real-time diagnostics overlay rendering the status variables: `FRONT`, `REAR`, `N`, and `Size`.
  * **Interactive Scenarios:** Features preset workflows demonstrating typical operations, item removals, handling structural boundaries, and tracking weight fine metrics.
  * **Boundary Protections:** Fully protects execution flow against Circular `Overflow` and `Underflow` states.

### 4. Doubly Linked List Manager (HW04)
Translates pointer manipulation and dynamic memory handling visually[cite: 1]. It converts the sports equipment inventory domain into a non-contiguous chain using custom references.

* **Core Structures:**
  * `Equipment`: Stores structural primitive definitions and parses image paths dynamically.
  * `DNode`: Features bidirectional object tracking variables (`INFOR`, `BACK`, `FORW`).
* **Core Features:**
  * **Forward Traversing Pointer Search:** Adapts textbook standard `PTR = PTR.FORW` routines into custom search functions.
  * **Dynamic Insertion/Deletion:** Safely updates links across boundaries, checking specific states for leading edges (`START = LOC.FORW`), trailing endpoints (`LAST = LOC.BACK`), or standard middle positions.

---

## 🛠️ Requirements & Installation

### Environment Setup
* **Language:** Java (JDK 23 or JDK 24 recommended)
* **IDE Support:** NetBeans IDE or Any standard Java build framework

> [!CAUTION]
> **Compilation Version Mismatch Note:**
> If you encounter a `java.UnsupportedClassVersionError` (e.g., class file version 68.0 compiled by a more recent runtime than the system target environment recognizes), ensure your project compilation settings align perfectly:
> 1. Right-click the root node inside your NetBeans **Projects** side panel $\rightarrow$ **Properties**.
> 2. Open **Sources** / **Build** $\rightarrow$ adjust **Source/Binary Format** to match your platform's active environment JDK configuration.

---

## 🚀 Getting Started

Every sub-module includes an isolation run file to execute components separately:

* **Array Application:** Run `src/hw01array/array1_1.java`
* **Stack Application:** Run `src/stack1/get_started.java`
* **Queue Application:** Run `src/hw03queue/getstarted1.java`
* **Linked List Application:** Run `src/hw04linkedlist/linkedlist1.java`

### Working Directory Data Format
To populate data tables using the `Import CSV` action menus, ensure file sets match the following configurations:

```csv
id,name,category,price
0,Stopwatch,Others,107.91
1,Volleyball,Ball Sports,181.20
2,Snorkel Set,Water Sports,123.46
```