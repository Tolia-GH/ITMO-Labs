import React from 'react';
import {Route,Routes} from 'react-router-dom';

import {Service1} from "./main/service1";

class App extends React.Component {
    render(){
        return(
            <div>
                <Routes>
                    <Route path="/" element={<Service1/>}/>
                </Routes>
            </div>
        )
    }
}
export default App;