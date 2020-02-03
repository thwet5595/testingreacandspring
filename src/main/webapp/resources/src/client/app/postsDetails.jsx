import React from 'react';
import { render } from 'react-dom';
import { Navbar, Button, FormGroup, FormControl, Nav, ListGroup, ListGroupItem, Modal } from 'react-bootstrap';
import $ from 'jquery';
import { Form, ControlLabel, Col, HelpBlock } from 'react-bootstrap';

class PostDetails extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            comments: [],
            postId: postId,
            title: "",
            questions: ""
        }
        this.addComment = this.addComment.bind(this);
    };

    componentWillMount() {
        console.log( "componentWillMount" );
        var url = '/user/posts/postId/' + this.state.postId + '/details';
        $.get( url ).done(( postandcommentresponse ) => {
            this.setState( {
                comments: postandcommentresponse.commentDto,
                title: postandcommentresponse.title,
                questions: postandcommentresponse.questions
            } );
            console.log( JSON.stringify( this.state.comments ) );
        } );
    };

    addComment(){
        var self = this;
        var comments = $( '#comments' ).val();
        console.log("Comment is::::"+comments);
        var postId = $('#postId').val();
        console.log("Post id is :::"+postId);
        var token = $( "meta[name='_csrf']" ).attr( "content" );
        var header = $( "meta[name='_csrf_header']" ).attr( "content" );
        var url = '/user/addComment/'+postId;
        $.ajax( {
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify( { comments:comments} ),
            dataType: 'json',
            url: url,
            beforeSend: function( xhr ) {
                console.log("Header is::"+header+":Token:::"+token);
                xhr.setRequestHeader( header, token );
            },
            success: function( updateComment ) {
                console.log( "After adding comment" );
                console.log( JSON.stringify( updateComment ) );
                self.setState( { comments: updateComment } );
                self.forceUpdate();
            },
            error: function( e ) {
                console.log( JSON.stringify( e ) );
            }
        } );
    }
    
    render() {
        return (
            <div className="panel-group">
                <Details postId={this.state.postId} questions={this.state.questions} title={this.state.title} />
                <Comments comments={this.state.comments} />
                <CommentForm addComment={this.addComment} postId= {this.state.postId} />
            </div>
        );
    }
}

class Details extends React.Component {
    constructor( props ) {
        super( props );
    };

    render() {
        return (
            <div className="panel-group">
                <ul style={{ listStyleType: 'none', paddingLeft: 'inherit' }}>
                    <li>
                        <div className="panel panel-default">
                            <div className="panel-heading">{this.props.questions}</div>
                        </div>
                        <br />
                    </li>
                </ul>
            </div>
        );
    }
}

class Comments extends React.Component {
    constructor( props ) {
        super( props );
    };

    render() {
        return (
            <div className="panel-group">
                <ul style={{ listStyleType: 'none', paddingLeft: 'inherit' }}>
                    {this.props.comments.map(( data, index ) =>
                        <li key={index}>
                            <div className="panel panel-default">
                                <div className="panel-body">{data.comments}</div>
                            </div>
                            <br />
                        </li>
                    )}
                </ul>
            </div>
        );
    }
}

class CommentForm extends React.Component {
    constructor( props ) {
        super( props );
        this.addComment = this.addComment.bind(this);
    };

    render() {
        return (
            <div>
                <form onSubmit={this.addComment}>
                    <input type="hidden" value={this.props.postId} id="postId" />
                    <label>Your Comment:</label>
                    <textarea className="form-control" rows="5" id="comments" />
                    <br />
                    <input type="submit" value="Comment" className="btn btn-primary pull-right" />
                </form>
            </div>
        );
    }
    
    addComment(event){
        event.preventDefault();
        this.props.addComment();
        $('#comments').val(" ");
    }
}

render( <PostDetails />, document.getElementById( 'details' ) );