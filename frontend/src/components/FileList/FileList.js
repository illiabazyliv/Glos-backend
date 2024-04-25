import FileItem from "../FileItem/FileItem";

function FileList({ files }) {
    return (
        <div className="file-list">
            <div className="row">
                {
                    files.map(item => <div className="col-12" key={item.displayFullName} >
                        <FileItem file={item} />
                    </div>)
                }
            </div>
        </div>
    );
}

export default FileList;