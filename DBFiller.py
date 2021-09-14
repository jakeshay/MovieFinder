import mysql.connector
import http.client
import json
import requests
from bs4 import BeautifulSoup

def getMovie(movieName):
    url = "https://movie-database-imdb-alternative.p.rapidapi.com/"
    correctAmp = movieName.replace("&amp;", "&")
    querystring = {"s":correctAmp,"page":"1","r":"json"}

    headers = {
        'x-rapidapi-host': "movie-database-imdb-alternative.p.rapidapi.com",
        'x-rapidapi-key': "8ca55b21e6msha37e66df719a38ap1fc3c2jsn802dc9396eed"
    }

    response = requests.request("GET", url, headers=headers, params=querystring)

    data = response.text
    data_dict = json.loads(data)
    try:
        results = data_dict['Search']
    except KeyError:
        imdb = "failed"
        return imdb, data_dict
    movie = results[0]
    imdb = movie['imdbID']

    conn = http.client.HTTPSConnection("movie-database-imdb-alternative.p.rapidapi.com")

    headers = {
        'x-rapidapi-host': "movie-database-imdb-alternative.p.rapidapi.com",
        'x-rapidapi-key': "8ca55b21e6msha37e66df719a38ap1fc3c2jsn802dc9396eed"
        }

    request = "/?i=" + imdb + "&r=json"
    conn.request("GET", request, headers=headers)

    res = conn.getresponse()
    data = res.read().decode("utf-8")
    data_dict = json.loads(data)

    print(movieName + " details have been found")

    return imdb, data_dict


def intoDB(mydb, imdb, data_dict):
    mycursor = mydb.cursor()

    sql = "INSERT INTO Movie(MovieID, Title, ReleaseYear, Rating, Runtime, Metascore, imdbRating) " \
          "VALUES (%s, %s, %s, %s, %s, %s, %s)"
    val = (imdb, data_dict['Title'], data_dict['Year'], data_dict['Rated'], data_dict['Runtime'],
           data_dict['Metascore'], data_dict['imdbRating'])

    try:
        mycursor.execute(sql, val)
        mydb.commit()
    except mysql.connector.errors.DatabaseError:
        return "failed"

    sql = "INSERT INTO Directors(Name, MovieID) VALUES (%s, %s)"
    split = data_dict['Director'].split(',')
    values = []
    for x in split:
        val = (x, imdb)
        values.append(val)

    mycursor.executemany(sql, values)
    mydb.commit()

    sql = "INSERT INTO Writers(Name, MovieID) VALUES (%s, %s)"
    split = data_dict['Writer'].split(',')
    values = []
    for x in split:
        val = (x, imdb)
        values.append(val)

    mycursor.executemany(sql, values)
    mydb.commit()

    sql = "INSERT INTO Genres(Name, MovieID) VALUES (%s, %s)"
    split = data_dict['Genre'].split(',')
    values = []
    for x in split:
        val = (x, imdb)
        values.append(val)

    mycursor.executemany(sql, values)
    mydb.commit()

    sql = "INSERT INTO Actors(Name, MovieID) VALUES (%s, %s)"
    split = data_dict['Actors'].split(',')
    values = []
    for x in split:
        val = (x, imdb)
        values.append(val)

    mycursor.executemany(sql, values)
    mydb.commit()

    return ""


def webScrape():
    movies = []
    response = requests.get("https://www.boxofficemojo.com/year/world/2018/?sort=domesticGrossToDate&ref_=bo_ydw__resort#table")
    soup = BeautifulSoup(response.text, 'html.parser')
    titles = soup.findAll('td', attrs={"a-text-left mojo-field-type-release_group"})
    for title in titles:
        tmp = str(title)
        parsed = tmp.split('>')
        remaining = parsed[2]
        clean = remaining.split('<')
        movies.append(clean[0])

    return movies

def fillDB():
    movies = webScrape()
    failed = []
    mydb = mysql.connector.connect(
            host = "localhost",
            user = "root",
            password = "",
            database = "MovieFinder"
        )

    for movie in movies[:10]:
        print("Currently adding: " + movie)
        imbd, data = getMovie(movie)
        if (imbd == "failed"):
            failed.append(movie)
            print("Could not find: " + movie)
        else:
            status = intoDB(mydb, imbd, data)
            if (status == "failed"):
                failed.append(movie)
                print("Could not add: " + movie)
            else:
                print("Successfully added: " + movie)

    print("Program failed to add the following movies:")
    for f in failed:
        print(f)

    mydb.close()


fillDB()
