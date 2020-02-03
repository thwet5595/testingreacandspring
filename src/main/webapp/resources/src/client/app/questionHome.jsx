import React from 'react';
import { render } from 'react-dom';
import { Navbar, Button, FormGroup, FormControl,Nav } from 'react-bootstrap';

class App extends React.Component {
    render() {
        var allUrl = "/user/all/questions";
        var myUrl = "/user/my/questions/";
        return (
            <div>
               
            </div>
        );
    }
}

render( <App />, document.getElementById( 'home' ) );
