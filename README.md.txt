# Library Management System

## General Overview
The **Library Management System** is a Java-based application that helps manage a library’s collection of items and its members. It allows users to track books, reference books, magazines, and audiobooks, handle borrowing and returning, manage members, and generate reports on loans and overdue items. The system offers both a console-based interface and a graphical interface built with Java Swing, making it easy and interactive to use.

---

## Key Features

### Item Management
- Supports different types of items: Books, Reference Books, Magazines, and Audiobooks.
- Add new items and search for items by title.
- Tracks the availability of items for borrowing.

### Member Management
- Add new library members.
- Track active loans for each member.
- Generate reports on loans and overdue items.

### Borrowing and Returning
- Members can borrow items if available and within loan limits.
- Borrowed items are automatically marked as unavailable.
- Reference books cannot be borrowed and remain in the library.
- Returning an item updates its status and removes it from the member’s active loans.

### Reports and Searches
- View all items or only available items.
- Search items by title.
- Check all members and their active loans.
- Generate overdue reports showing items past due dates and the corresponding members.

### Graphical User Interface (GUI)
- Built using Java Swing.
- Tabbed panes for easy navigation between items and members.
- Buttons for quick actions like Borrow, Return, Search, Add Item/Member.
- Dynamic tables display current items and members with their status.

---

## How It Works

### Initialization
- The system starts with sample data: books, magazines, audiobooks, and members.
- GUI tables are populated with this data.

### User Interaction
- Users interact via buttons or menu options.
- Borrowing or returning items prompts for member ID and item ID.
- Adding items or members prompts for required information.

### Business Logic
- Core logic is handled in the `Library` class.
- Methods ensure data validation: item availability, member existence, loan limits.
- Custom exceptions handle invalid operations (e.g., borrowing unavailable items).

---

## Key Classes and Methods

### 1. Item (abstract class)
- Base class for all library items.
- Tracks `id`, `title`, and availability.
- Abstract method `getItemType()` for each item type.
- `canBeBorrowed()` defaults to `true`; ReferenceBooks override this.

### 2. Book (extends Item)
- Represents a borrowable book.
- Adds `author` field.
- Overrides `getDefaultLoanDays()` to 21 days.
- Two constructors: with and without author.

### 3. ReferenceBook (extends Book)
- Cannot be borrowed.
- Overrides `canBeBorrowed()` and `setAvailable()` to always be false.
- Displays as `"Reference Book (In-Library)"`.

### 4. Audiobook (extends Item)
- Digital or recorded books with `narrator` and `lengthMinutes`.
- Loan period: 28 days.
- `toString()` includes narrator and length.

### 5. Magazine (extends Item)
- Periodicals with `issueNumber`.
- Loan period: 7 days.
- `toString()` shows issue number.

### 6. Member
- Represents a library user.
- Tracks `activeLoans` and ensures borrowing limits.
- Methods: `addLoan()`, `returnLoan()`, `getOverdueLoans()`.

### 7. Loan
- Represents a borrowing event (item, date borrowed, due date).
- Methods: `isOverdue()`, `daysOverdue()`.

### 8. Library
- Core system managing items, members, borrowing, returning, reporting.
- Handles rules: item availability, loan limits, reference book restrictions.
- Returns copies of lists to prevent external modification.
- Throws custom exceptions for invalid operations.

### 9. Custom Exceptions
- Clear error handling:
  - `BookNotAvailableException`
  - `MemberNotFoundException`
  - `ItemNotFoundException`
  - `MaxLoansExceededException`

### 10. LibraryGUI
- User-friendly graphical interface using Swing.
- JTabbedPane separates Items and Members.
- JTable displays items and members dynamically.
- Buttons: Borrow, Return, Search, Add (update Library and refresh tables).

---

## Conclusion
This project provides a practical solution for managing a library in a small-scale setting. It combines functional console operations with an intuitive GUI. It demonstrates:

- Object-Oriented Programming
- Exception Handling
- Java Collections
- Swing GUI Design
- Modular, maintainable code

The system is easy to extend for future improvements.
