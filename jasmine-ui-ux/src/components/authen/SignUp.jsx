import React, { useState } from "react";
import { NavLink, Navigate, useNavigate } from "react-router-dom";
import {
  Button,
  Form,
  Grid,
  Segment,
  Message,
  Divider,
  Icon,
} from "semantic-ui-react";
import OtpInput from "react-otp-input";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAuth } from "../context/AuthContext";
import signUpImg from "../../assets/images/jas-signup.png";
import { handleLogError } from "../misc/Helpers";
import { businessApi } from "../misc/BusinessApi";
import "./authen.css";

function SignUp() {
  const Auth = useAuth();
  const isLoggedIn = Auth.userIsAuthenticated();
  const [otp, setOtp] = useState("");
  const navigate = useNavigate();

  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const [isHiddenCheckEmailForm, setIsHiddenCheckEmailForm] = useState(true);
  const [isHiddenOTPForm, setIsHiddenOTPForm] = useState(false);
  const [isHiddenSignupForm, setIsHiddenSignupForm] = useState(false);
  const [passwordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const initialValues = {
    email: "",
    name: "",
    username: "",
    password: "",
    cpassword: "",
  };

  const [signupForm, setSignupForm] = useState(initialValues);

  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSignupForm({
      ...signupForm,
      [name]: value,
    });
  };

  const validateForm = (values) => {
    const error = {};
    const regex = /^[^\s+@]+@[^\s@]+\.[^\s@]{2,}$/i;
    if (!values.email) {
      error.email = "Email is required";
    }
    // if (!values.name) {
    //   error.name = "Full Name is required";
    // }
    if (!values.username) {
      error.username = "Username is required";
    } else if (!regex.test(values.email)) {
      error.email = "This is not a valid email format!";
    }
    if (!values.password) {
      error.password = "Password is required";
    } else if (values.password.length < 4) {
      error.password = "Password must be more than 4 characters";
    } else if (values.password.length > 30) {
      error.password = "Password cannot exceed more than 30 characters";
    }
    if (!values.cpassword) {
      error.cpassword = "Confirm Password is required";
    } else if (values.cpassword !== values.password) {
      error.cpassword = "Confirm password and password should be same";
    }
    return error;
  };

  const checkEmailHandler = async (e) => {
    e.preventDefault();

    if (!signupForm.email) {
      setIsError(true);
      return;
    }

    try {
      const response = await businessApi.checkSignupEmail(signupForm.email);
      localStorage.setItem("OTP", response.data);
      setIsHiddenCheckEmailForm(false);
      setIsHiddenOTPForm(true);
      setIsError(false);
      toast.success(`OTP was sent to ${signupForm.email}`, {
        position: "top-center",
        autoClose: 2000,
      });
    } catch (error) {
      handleLogError(error);
      if (error.response && error.response.data) {
        const errorData = error.response.data;
        let errorMessage = "Invalid fields";
        if (errorData.status === 409) {
          errorMessage = errorData.message;
        } else if (errorData.status === 400) {
          errorMessage = errorData.errors[0].defaultMessage;
        }
        setIsError(true);
        setErrorMessage(errorMessage);
      }
    }
  };

  const checkOTPHandler = (e) => {
    e.preventDefault();
    const otpCode = localStorage.getItem("OTP");
    if (otp === otpCode) {
      setTimeout(() => {
        setIsHiddenOTPForm(false);
        setIsHiddenSignupForm(true);
        setIsError(false);
      }, 2000);
    } else {
      setIsError(true);
      setErrorMessage("OTP is invalid !");
    }
  };

  const signupHandler = async (e) => {
    e.preventDefault();
    setFormErrors(validateForm(signupForm));
    console.log("form errors", formErrors);
    if (Object.keys(formErrors).length !== 0) {
      console.log("form errors", formErrors);
      return;
    } else {
      try {
        const response = await businessApi.signup(signupForm);
        const { accessToken } = response.data;

        toast.success("ĐĂNG KÝ THÀNH CÔNG!", {
          position: "top-center",
          className: "toast-success",
        });
        navigate("/login");
        setSignupForm(initialValues);
      } catch (error) {
        handleLogError(error);
        if (error.response && error.response.data) {
          const errorData = error.response.data;
          let errorMessage = "Invalid fields";
          if (errorData.status === 409) {
            errorMessage = errorData.message;
          } else if (errorData.status === 400) {
            errorMessage = errorData.errors[0].defaultMessage;
          }
          setIsError(true);
          setErrorMessage(errorMessage);
        }
      }
    }
    setIsSubmit(true);
  };

  if (isLoggedIn) {
    return <Navigate to="/" />;
  }

  return (
    <div>
      <div className="loginImage text-center">
        <img
          className="formImage"
          src={signUpImg}
          width="300"
          style={{ position: "relative" }}
          alt="login"
        />
      </div>
      <div className="center">
        <div className="authenForm">
          <Grid textAlign="center">
            <Grid.Column style={{ maxWidth: 360 }}>
              {isHiddenCheckEmailForm && (
                <Form
                  className="userForm"
                  size="large"
                  onSubmit={checkEmailHandler}
                >
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
                      placeholder="Enter your email to sign up..."
                      type="email"
                      value={signupForm.email}
                      onChange={handleInputChange}
                    />

                    <Button color="pink" fluid size="large">
                      Sign up
                    </Button>
                  </Segment>
                </Form>
              )}

              {isHiddenOTPForm && (
                <form onSubmit={checkOTPHandler}>
                  <div className="otp-form">
                    <h4>Please enter the OTP from you email:</h4>
                    <div className="center mb-3">
                      <OtpInput
                        value={otp}
                        onChange={setOtp}
                        autoFocus
                        numInputs={6}
                        // placeholder={[0, 0, 0, 0, 0, 0]}
                        renderSeparator={<span>-</span>}
                        renderInput={(props) => <input {...props} />}
                        inputStyle={{
                          width: "1.75rem",
                          height: "2.5rem",
                          fontSize: "18px",
                          borderRadius: "5px",
                          border: "1px solid #ccc",
                          margin: "0 4px",
                          textAlign: "center",
                        }}
                      />
                    </div>
                    <Button className="m-0" color="orange" fluid size="large">
                      Confirm
                    </Button>
                  </div>
                </form>
              )}

              {isHiddenSignupForm && (
                <Form
                  size="large"
                  className="userForm"
                  onSubmit={signupHandler}
                >
                  <Segment>
                    <Form.Input
                      fluid
                      autoFocus
                      name="username"
                      action={{
                        icon: "user",
                        tabIndex: -1,
                        //   style: {
                        //     background: "#F2711C",
                        //     opacity: "50%",
                        //     color: "white",
                        //   },
                      }}
                      actionPosition="left"
                      actionDisabled="true"
                      id="username"
                      placeholder="Username"
                      value={signupForm.username}
                      onChange={handleInputChange}
                    />
                    <Form.Input
                      fluid
                      name="password"
                      type={passwordVisible ? "text" : "password"}
                      icon={
                        <Icon
                          name={passwordVisible ? "eye" : "eye slash"}
                          link
                          onClick={togglePasswordVisibility}
                        />
                      }
                      iconPosition="right"
                      action={{
                        icon: "lock",
                        tabIndex: -1,
                        //   style: {
                        //     background: "#F2711C",
                        //     opacity: "50%",
                        //     color: "white",
                        //   },
                      }}
                      actionPosition="left"
                      placeholder="Password"
                      value={signupForm.password}
                      onChange={handleInputChange}
                    />
                    <Form.Input
                      fluid
                      name="cpassword"
                      type={passwordVisible ? "text" : "password"}
                      icon={
                        <Icon
                          name={passwordVisible ? "eye" : "eye slash"}
                          link
                          onClick={togglePasswordVisibility}
                        />
                      }
                      iconPosition="right"
                      action={{
                        icon: "lock",
                        tabIndex: -1,

                        //   style: {
                        //     background: "#F2711C",
                        //     opacity: "50%",
                        //     color: "white",
                        //   },
                      }}
                      actionPosition="left"
                      placeholder="Confirm Password"
                      value={signupForm.cpassword}
                      onChange={handleInputChange}
                    />
                    {/* <Form.Input
                      fluid
                      name="name"
                      action={{
                        icon: "address card",
                        tabIndex: -1,
                          style: {
                            background: "#F2711C",
                            opacity: "50%",
                            color: "white",
                          },
                      }}
                      actionPosition="left"
                      actionDisabled="true"
                      placeholder="Name"
                      value={signupForm.name}
                      onChange={handleInputChange}
                    /> */}

                    <Button color="orange" fluid size="large">
                      Signup
                    </Button>
                  </Segment>
                </Form>
              )}
              {isError && <Message negative>{errorMessage}</Message>}
              <Message>
                {`Already have an account? `}
                <NavLink to="/login" color="purple">
                  <strong>Login</strong>
                </NavLink>
              </Message>

              <Divider horizontal>have a nice day !</Divider>
            </Grid.Column>
          </Grid>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
