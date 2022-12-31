package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.*;
import com.example.library.studentlibrary.repositories.BookRepository;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    int max_allowed_books;

    @Value("${books.max_allowed_days}")
    int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases

       // return null; //return transactionId instead
        if (bookRepository5.existsById(bookId) && bookRepository5.findById(bookId).get().isAvailable()) ;
        else {
            throw new Exception("Book is either unavailable or not present");
            break;
        }
        if (!cardRepository5.existsById(cardId)) {
            throw new Exception("Card is invalid");
            break;
        }
        List<Book> books = bookRepository5.findAll();
        int count =0;
        for(Book b : books)
        {
            if(b.getCard().getId()== cardId) count = count + 1;
        }
        if(count>= max_allowed_books) {
            throw new Exception("Book limit has reached for this card");
            break;
        }
        bookRepository5.findById(bookId).get().setAvailable(false);
        bookRepository5.findById(bookId).get().setCard(cardRepository5.findById());
        Transaction t1 = new Transaction();
        t1.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        t1.setBook(bookRepository5.findAll(bookId));
        transactionRepository5.save(t1);
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);
        Transaction t1 = new Transaction();
        if(t1.getTransactionDate()>getMax_allowed_days)
        t1.fineAmount =(t1.getTransactionDate()-getMax_allowed_days)*fine_per_day;
        bookRepository5.findById(bookId).get().setAvailable(true);
        transactionRepository5.save(t1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = null;
        return t1; //return the transaction after updating all details
    }
}