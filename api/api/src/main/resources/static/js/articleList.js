updateArticleList();

async function updateArticleList()
{
    getLatestArticles()
    .then((response) => 
    {
        const articleList = response.articulos;
    
        const lstArticles = document.getElementById("latest-articles");
        lstArticles.innerHTML = "";

        for(let i = 0; i < articleList.length; i++)
        {
            const itmArticle = document.createElement("li");
            itmArticle.classList = "select-cursor";
            itmArticle.textContent = articleList[i].titulo;
            itmArticle.onclick = function()
            {
                window.location.href = `/articulo/index.html?fecha=${articleList[i].fecha}`;
            };
            lstArticles.appendChild(itmArticle);
        }
    });
}