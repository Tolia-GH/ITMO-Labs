import React from 'react';
import {Route,Routes} from 'react-router-dom';

import {Main} from "./main/main";

class App extends React.Component {
    render(){
        return(
            <div>
                <Routes>
                    <Route path="/" element={<Main/>}/>
                </Routes>
            </div>
        )
    }
}
export default App;