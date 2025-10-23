import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
  Form,
  Grid,
  Segment,
  Message,
  Divider,
} from "semantic-ui-react";
import { useAuth } from "../context/AuthContext";
import { handleLogError } from "../misc/Helpers";
import forgotPassImg from "../../assets/images/forgot-pass.png";
import "./authen.css";
import { businessApi } from "../misc/BusinessApi";

function ForgotPassword() {
  const Auth = useAuth();
  const isLoggedIn = Auth.userIsAuthenticated();

  const [email, setEmail] = useState("");
  const [isError, setIsError] = useState(false);
  const [isDisable, setIsDisable] = useState(true);
  let navigate = useNavigate();

  const handleInputChange = (e, { value }) => {
    setEmail(value);

    if (value === "") {
      setIsDisable(true);
    } else {
      setIsDisable(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email) {
      setIsError(true);
      return;
    }

    try {
      const response = await businessApi.forgotPassword(email);
      console.log("RESPONE RS", response);
      navigate("/login");
    } catch (error) {
      handleLogError(error);
      setIsError(true);
    }
  };

  if (isLoggedIn) {
    navigate("/");
  }

  return (
    <div>
      <div className="loginImage text-center">
        <img
          className="formImage"
          src={forgotPassImg}
          width="200"
          style={{ position: "relative" }}
          alt="login"
        />
      </div>
      <div className="center">
        <div className="authenForm">
          <Grid textAlign="center">
            <Grid.Column style={{ maxWidth: 360 }}>
              <div className="info-field">
                <h5>Enter your email to update new password..</h5>
              </div>
              <Form className="userForm" size="large" onSubmit={handleSubmit}>
                <Segment>
                  <Form.Input
                    fluid
                    autoFocus
                    name="email"
                    action={{
                      icon: "at",
                      tabIndex: -1,
                      // color: 'orange'
                      // style: {
                      //     background: '#F2711C',
                      //     opacity: '50%',
                      //     color: 'white'
                      // },
                    }}
                    actionPosition="left"
                    placeholder="Enter your email..."
                    type="email"
                    onChange={handleInputChange}
                  />

                  <Button
                    color="orange"
                    fluid
                    size="large"
                    disabled={isDisable}
                  >
                    Reset my password
                  </Button>
                </Segment>
              </Form>

              {isError && (
                <Message negative>
                  <span>The email is incorrect, please try again!</span>
                </Message>
              )}

              <Divider horizontal>have a nice day !</Divider>
            </Grid.Column>
          </Grid>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
