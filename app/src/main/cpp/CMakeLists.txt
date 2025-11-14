cmake_minimum_required(VERSION 3.22.1)

project("edgedetection")

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find OpenCV
set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../../../opencv/sdk/native/jni)
find_package(OpenCV REQUIRED)

# Include directories
include_directories(${OpenCV_INCLUDE_DIRS})

# Add your native library
add_library(
        edgedetection
        SHARED
        native-lib.cpp
)

# Link libraries
target_link_libraries(
        edgedetection
        ${OpenCV_LIBS}
        android
        log
        jnigraphics
        GLESv2
        EGL
)