import FileItem from "../FileItem/FileItem";

function Pagination({ currentPage, totalPages, onPageChange, }) {
    const pages = [];
    for(let i = 1; i <= totalPages; i++)
        pages.push(i);

    const goToPrevPage = () => {
        if(currentPage == 1) return;
        onPageChange(currentPage - 1);
    }

    const goToNextPage = () => {
        if(currentPage == totalPages) return;
        onPageChange(currentPage + 1);
    }

    return (
        <div className="list-pagination mt-3">
            <div className="row">
                <div className="col-12 d-flex justify-content-center">
                    <ul className="pagination">
                        <li className="page-item" onClick={goToPrevPage}>
                            <a className="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>

                        {
                            pages.map(page => {
                                return <li className={`page-item ${page == currentPage ? 'active' : ''}`} 
                                            key={page}
                                            onClick={() => {onPageChange(page)}}>
                                        <a className="page-link" href="#">{page}</a>
                                    </li>
                            })
                        }

                        <li className="page-item" onClick={goToNextPage}>
                            <a className="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Pagination;