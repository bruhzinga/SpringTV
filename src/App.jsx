import './App.css';
import React from 'react';
import {Route, Routes} from 'react-router-dom';
import {RegisterPage} from "./Pages/RegisterPage/RegisterPage.jsx";
import {MoviePage} from "./Pages/MoviePage/MoviePage.jsx";
import {AllMoviesPage} from "./Pages/AllmoviesPage/AllMoviesPage.jsx";
import {LoginPage} from "./Pages/LoginPage/LoginPage.jsx";
import {History} from "./Pages/History.jsx";
import {Favourites} from "./Pages/Favourites.jsx";
import {SearchMoviesPage} from "./Pages/SearchMoviePage/SeachMoviePage";



function App() {
    return (
        <div className="pageContainer">
            <Routes>
                <Route path="/login" element={<LoginPage/>} />
                <Route path="/register" element={<RegisterPage/>} />
                <Route path="/movies" element={<AllMoviesPage/>} />
                <Route path="/movies/:id" element={<MoviePage/>} />
                <Route path="/history" element={<History/>} />
                <Route path="/favourites" element={<Favourites/>} />
                <Route path="/" element={<LoginPage/>} />
                <Route path="/search" element={<SearchMoviesPage/>} />


            </Routes>
        </div>
    );
}

export default App;
