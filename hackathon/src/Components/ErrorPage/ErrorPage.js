import React from 'react';
import './styles.scss';
import {Link} from 'react-router-dom';

function Errorpage() {
    return(
    <div className='error-page'>
        <div className='error-msg'>
            Oops! You are not authorized to access the location!
            <div className ='redirect'>
                Kindly <Link className to='/login'>Login</Link> to access the page!
            </div>
        </div>
    </div>
    );
}

export default Errorpage;