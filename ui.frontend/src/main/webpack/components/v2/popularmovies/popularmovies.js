$(function() {
    const API_KEY = process.env.API_KEY;
  

    var popularMovies = $(".popular-movies-list-v2 .cmp-image-list");

    if (!popularMovies.length) {
        return;
    }

    function popularMovieCardTemplate(movie) {
        return `
            <li class="cmp-image-list__item">
                <article class="cmp-image-list__item-content">
                    <a class="cmp-image-list__item-image-link">
                       <div class="cmp-image-list__item-image">
                            <img src="https://image.tmdb.org/t/p/w300${movie.poster_path}" alt="${movie.title}" class="cmp-image">
                        </div>
                    </a>
                    <a class="cmp-image-list__item-title-link" href="/us/en/magazine/guide-la-skateparks.html" data-cmp-clickable="">
                        <span class="cmp-image-list__item-title">${movie.title}</span>
                    </a>
                    <span class="cmp-image-list__item-description">${movie.overview}</span>
                </article>
            </li> 
        `;
    }

    function showPopularMovies(movies) {
        var movieCards = "";
        movies.forEach(function(movie) {
            movieCards += popularMovieCardTemplate(movie);
        });
        popularMovies.append(movieCards);
    }

    function requestPopularMovies(url, data, headers) {
        $.ajax({
            url: url,
            type: "GET",
            data: data,
            headers: headers,
            success: function(response) {
               
                showPopularMovies(response.results.slice(1,18));
            },
            error: function() {
                popularMovies.append('<p>Error loading popular movies.</p>');
            }
        });
    }

    function init(pagination) {
        var url = "https://api.themoviedb.org/3/movie/popular";
        var data = {
            language: "pt-BR",
            page: pagination
        };
        var headers = {
            Authorization: "Bearer " + API_KEY
        };

        requestPopularMovies(url, data, headers);
    }

    init(1);
});