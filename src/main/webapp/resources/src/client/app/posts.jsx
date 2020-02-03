import React from 'react';
import { render } from 'react-dom';
import { Navbar, Button, FormGroup, FormControl, Nav, ListGroup, ListGroupItem, Modal } from 'react-bootstrap';
import $ from 'jquery';
import { Form, ControlLabel, Col, HelpBlock } from 'react-bootstrap';

class App extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            posts: [],
            showPostForm: false
        }
        this.save = this.save.bind( this );
    };

    componentWillMount() {
        console.log( "componentWillMount" );
        var url = '/user/posts/all';
        $.get( url ).done(( posts ) => {
            this.setState( { posts: posts } );
            console.log( JSON.stringify( posts ) );
        } );
    };

    save() {
        console.log( "Enter save method:" );
        var self = this;
        var title = $( '#title' ).val();
        console.log( "Title is:::" + title );
        var questions = $( '#question' ).val();
        console.log( "Question is :::" + question );
        var token = $( "meta[name='_csrf']" ).attr( "content" );
        var header = $( "meta[name='_csrf_header']" ).attr( "content" );
        var url = "/user/add/posts";
        $.ajax( {
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify( { title: title, questions: questions } ),
            dataType: 'json',
            url: url,
            beforeSend: function( xhr ) {
                console.log( "Header is::" + header + ":Token:::" + token );
                xhr.setRequestHeader( header, token );
            },
            success: function( posts ) {
                console.log( "Enter success:" );
                self.setState( { posts: posts } );
                console.log( posts );
                self.forceUpdate();
            },
            error: function( e ) {
                console.log( "Enter Error:" );
                console.log( JSON.stringify( e ) );
            }
        } );
    }

    render() {
        return (
            <div>
                <PostsNav showPostForm={this.state.showPostForm} save={this.save} />
                <Posts posts={this.state.posts} />
            </div>
        );
    }
}

class PostsNav extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            showPostForm: this.props.showPostForm
            }
        this.handleClick = this.handleClick.bind( this );
        this.close = this.close.bind( this );
    }

    close() {
        this.setState( {
            showPostForm: false
        } );
    }

    handleClick() {
        console.log( "On handle click:::" );
        console.log( "Enter handle click" );
        var self = this;
        self.setState( {
            showPostForm:true
        } );
        console.log( "Show post from value in handle click:::"+self.state.showPostForm );
    }

    render() {
        var allUrl = "/user/all/questions";
        var myUrl = "/user/my/questions";
        var addUrl = "/user/add/posts";
        return (
            <div>
                <Navbar>
                <Navbar.Header>
                  <Navbar.Brand>
                    <a href="#home">Home</a>
                  </Navbar.Brand>
                    <Navbar.Brand>
                    <a href="#home">My Posts</a>
                  </Navbar.Brand>
                </Navbar.Header>
                    <Navbar.Form pullRight>
                    <Button type="submit" onClick={this.handleClick} >Submit</Button>
                  </Navbar.Form>
              </Navbar>
                <AddPosts close={this.close} showPostForm={this.state.showPostForm} save={this.props.save} />
            </div>
        );
    }
}

class Posts extends React.Component {
    render() {
        return (
            <div>
                <ul style={{ listStyleType: 'none', paddingLeft: 'inherit' }}>
                    {this.props.posts.map(( data, index ) =>
                        <li key={index}>
                            <div className="panel panel-default">
                                <div className="panel-heading">{data.title}</div>
                                <div className="panel-body"><a href={'/user/posts/details/' + data.id}>{data.questions}</a></div>
                            </div>
                            <br />
                        </li>
                    )}
                </ul>
            </div>
        );
    }
}

class AddPosts extends React.Component {
    constructor( props ) {
        super( props );
        var self = this;
        this.state = {
            showPostForm: true
        }
        this.savePost = this.savePost.bind( this );
    };

    savePost() {
        console.log( "Enter save posts::" );
        this.props.close();
        this.props.save();
    }

    render() {
        return (
            <div>
                <Modal onHide={this.props.close} show={this.props.showPostForm}>
                    <Modal.Body>
                        <h4></h4>
                        <form>
                            <label>Question Title:</label>
                            <input type="text" className="form-control" placeholder="title" id="title" />
                            <br />
                            <label>Your Question:</label>
                            <textarea className="form-control" rows="5" id="question" ref="comment" />
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.props.close}>Close</Button>
                        <Button id="event-form-button" onClick={this.savePost}>Post</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}
render( <App />, document.getElementById( 'post' ) );
