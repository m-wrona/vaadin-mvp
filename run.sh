#!/bin/bash

mvn clean install
cd web
mvn jetty:run
