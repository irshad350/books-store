# Books-store

Sample Books app is built with graphql, spring boot, mongodb and Docker(Stay tuned for kubernetes).

## Run with Docker

If you have the docker-desktop installed in your machine. You no need to install java 11 and mongodb.
If you don't have the docker follow the [Docker-Desktop-Download](https://www.docker.com/get-started)

### Commands:
cd {ProjectPath}/graphql-springboot-mongodb-master
```
docker build -t api-docker-image .
docker-compose up -d
```

## Run without Docker

Prerequisites
[Java 11](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip), 
[MongoDB](https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2012plus-4.2.6-signed.msi)

### Steps to build jar file
cd {ProjectPath}/graphql-springboot-mongodb-master <br />
mvn clean package

## Graphql Queries

Use [http://localhost:9092/graphiql](http://localhost:8080/graphiql) to execut queries. <br />

### Select Query for All books with authors
```
{
  findAllBooks {
    id
    isbn
    title
    pageCount
    author {
      firstName
      lastName
    }
  }
}
```

### Insert Query for new book with author id without variables
```
mutation {
  newBook(
    title: "Java: The Complete Reference, Tenth Edition", 
    isbn: "1259589331", 
    author: 1) {
      id title
  }
}
```

### Insert Query for New Author
```
mutation ($one:String!, $two:String!) {
  newAuthor (firstName:$one, lastName:$two) {
    firstName
    lastName
    id
  }
}
# INPUT #
{
  "one":"author_firstname",
  "two":"author_lastname"
}

```

### Insert Query for New Book
```
mutation ($title:String!, $isbn:String!, $pageCount:Int!) {
  newBook(title: $title, isbn: $isbn, pageCount:$pageCount, author: "5ec2245fed342909d54abc2a") {
    title
  }
}

# INPUT #
{
  "title":"Best Java Book",
  "isbn":"i don't know",
  "pageCount":2000
}
```
### Insert Query for New Book with New Author
```
mutation ($title:String!, $isbn:String!, $pageCount:Int!, $authorInput:AuthorInput!) {
  newBookWithNewAuthor(title: $title, isbn: $isbn, pageCount:$pageCount, authorInput: $authorInput) {
    title, 
    author {
      firstName
    }
  }
}

# INPUT #
{
  "title":"newbook",
  "isbn":"newbook-isbn",
  "pageCount":2000,
  "authorInput": {
    "firstName": "isd",
    "lastName": "mhd"
  }
}
```
### Query for Update Book
```
mutation {
  updateBookPageCount(pageCount: 1344, id: "5ec2306600c92e6eb6cc0fee") {
    id pageCount
  }
}

```
### Query for Delete Book
```
mutation {
  deleteBook(id: "5ec2306600c92e6eb6cc0fee")
}
```
