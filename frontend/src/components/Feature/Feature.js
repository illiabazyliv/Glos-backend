function Feature({item}) {
  return (
    <div className="card feature">
        <div className="card-body text-center">
            <div className='card-img d-flex align-items-center justify-content-center pt-3'>
                <div className='card-icon rounded-circle d-flex align-items-center justify-content-center'>
                    <i className={item.iconClass}></i>
                </div>
            </div>
            <h5 className="card-title pb-1">{item.title}</h5>
            <p className="card-text">{item.text}</p>
        </div>
    </div>
  );
}

export default Feature;