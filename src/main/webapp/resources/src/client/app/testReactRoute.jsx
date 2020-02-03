import React from 'react';
import { render } from 'react-dom';
import {
    BrowserRouter as Router,
    Route,
    Link
} from 'react-router-dom'

class TestReact extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            posts: [],
            showPostForm: false
        }
    };

    render() {
        return (
            <Router>
                <div>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/about">About</Link></li>
                        <li><Link to="/topics">Topics</Link></li>
                    </ul>

                    <hr />

                    <Route exact path="/" component={Home} />
                    <Route path="/about" component={About} />
                    <Route path="/topics" component={Topics} />
                </div>
            </Router>
        );
    }
}

class Home extends React.Component {
    render() {
        return (
            <div>
                <h2>Home</h2>
            </div>
        );
    }
}

class About extends React.Component {
    render() {
        return (
            <div>
                <h2>About</h2>
            </div>
        );
    }
}

class Topics extends React.Component {
    constructor( props ) {
        super( props );
    };
    render() {
        return ( <Router>
                <div>
            <h2>Topics</h2>
            <ul>
                <li>
                    <Link to="/topics/rendering">
                        Rendering with React
                  </Link>
                </li>
                <li>
                    <Link to="/topics/components">
                        Components
                  </Link>
                </li>
                <li>
                    <Link to="/topics/props-v-state">
                        Props v. State
                  </Link>
                </li>
            </ul>

            <Route path="/topics/:topicId" component={Topic} />
            <Route exact path="/topics" render={() => (
                <h3>Please select a topic.</h3>
            )} />
        </div>
    </Router>
        );
    }
}

class Topic extends React.Component {
    constructor( props ) {
        super( props );
    };

    render() {
        console.log( "Enter Topic Class:::" );
        return (
            <div>
                <h2>Topic id</h2>
                <h3>{this.props.params.topicId}</h3>
            </div>
        );
    }
}
render( <TestReact />, document.getElementById( 'testReactRoute' ) );