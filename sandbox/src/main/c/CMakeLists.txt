cmake_minimum_required(VERSION 2.5)
project(judger C)

#set(CMAKE_VERBOSE_MAKEFILE ON)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/output)

# set(CMAKE_C_FLAGS "-g -Wall -Werror -O3 -std=c99 -pie -fPIC")
set(CMAKE_C_FLAGS "-g -Wall -std=c11 -pie -fPIC -shared")

# make judger lib
file(GLOB SOURCE "sandbox/src/*.c" "sandbox/src/rules/*.c" "*.c")
include_directories( "include/" "include/linux")
add_executable(sandbox.so ${SOURCE})
target_link_libraries(sandbox.so pthread seccomp)


install(FILES output/sandbox.so
    PERMISSIONS OWNER_EXECUTE OWNER_READ
    DESTINATION /usr/lib/sandbox/)
