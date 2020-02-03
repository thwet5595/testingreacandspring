import React from 'react';
import {render} from 'react-dom';
import { Button,ButtonToolbar,Tabs,Tab } from 'react-bootstrap';

class App extends React.Component {
  render() {
    return (
        <div>
          <p> Hello React2!</p>
          <Tabs defaultActiveKey={2} id="uncontrolled-tab-example">
          <Tab eventKey={1} title="Tab 1">
              Tab 1 content
          </Tab>
          <Tab eventKey={2} title="Tab 2">
              Tab 2 content
          </Tab>
          <Tab eventKey={3} title="Tab 3">
              Tab 3 content
          </Tab>
      </Tabs>
        </div>
    );
  }
}

render(<App/>, document.getElementById('app'));
