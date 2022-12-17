import './App.css';
import React from 'react';
import {Route, Routes} from 'react-router-dom';
import {RegisterPage} from "./Pages/RegisterPage/RegisterPage.jsx";
import {MoviePage} from "./Pages/MoviePage/MoviePage.jsx";
import {AllMoviesPage} from "./Pages/AllmoviesPage/AllMoviesPage.jsx";
import {LoginPage} from "./Pages/LoginPage/LoginPage.jsx";
import {History} from "./Pages/History.jsx";
import {Favourites} from "./Pages/Favourites.jsx";


function App() {
    return (
        <div className="pageContainer">
            <Routes>
                <Route path="/" element={<LoginPage/>} />
                <Route path="/register" element={<RegisterPage/>} />
                <Route path="/allMovies" element={<AllMoviesPage/>} />
                <Route path="/movies/:id" element={<MoviePage/>} />
                <Route path="/history" element={<History/>} />
                <Route path="/favourites" element={<Favourites/>} />
            </Routes>
        </div>
    );
}

export default App;
