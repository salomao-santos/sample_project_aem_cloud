$(function() {
    const API_KEY = process.env.API_KEY;
  

    var popularMovies = $(".popular-movies-v1 .aem-Grid");

    if (!popularMovies.length) {
        return;
    }

    function popularMovieCardTemplate(movie) {
        return `
            <div class="teaser aem-GridColumn--default--none aem-GridColumn aem-GridColumn--offset--default--0 aem-GridColumn--default--4">
                <div class="cmp-teaser">
                    <div class="cmp-teaser__image">
                        <div class="cmp-image">
                            <img src="https://image.tmdb.org/t/p/w200${movie.poster_path}" class="cmp-image__image" alt="${movie.title}">
                        </div>
                    </div>
                    <div class="cmp-teaser__content">
                        <h2 class="cmp-teaser__title">${movie.title}</h2>
                        <div class="cmp-teaser__description">
                            <p>${movie.overview}</p>
                        </div>
                    </div>
                    
                </div>
            </div>
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