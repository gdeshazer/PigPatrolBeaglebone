/*
 * tmp102.c
 *
 * This is the JNA class that receives data from the arduino
 *
 * Currently hard coded for two devices and will only receive
 * 2 floats through the I2C lines.
 *
 * Based on example code from:
 * 		http://www.eshayne.com/jnaex/index.html?example=9
 * 		http://bradsmc.blogspot.com/2013/10/using-java-jna-to-access-i2c-device-on.html
 *
 *	On the Beaglebone, compile this with
 *		gcc -fPIC tmp102.c -o tmp102.so -shared
 *
 *	Be sure that the LD_LIBRARY_PATH is set to the location where tmp102.so is set
 *		bash cmd to set path
 *		export LD_LIBRARY_PATH=/Path/to/the/correct/file.so
 */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <fcntl.h>
#include <linux/i2c-dev.h>
#include <linux/i2c.h>
#include "tmp102.h"

//#define I2C_SLAVE 0x13

void getBytes(int *numDev, float** returnArray) {
	//hard coded for 2 devices...pass in?
	*numDev = 2;

	//hard coded for a byte array of 8 IE 2 floats
	unsigned char input[8];

	//union here converts the floats into individual bytes
	//union works by having the variables in it share the
	//same memory address
	union u_tag{
		unsigned char b[4];
		float fval;
	} u[*numDev];

	int devHandle;
	int readBytes;

	//hard coded currently...pass in?
	int devI2CAddr = 0x10;

	// open device on /dev/i2c-1
	if ((devHandle = open("/dev/i2c-1", O_RDWR)) < 0) {
		printf("Error: Couldn't open device! %d\n", devHandle);
	}

	// connect as i2c slave
	if (ioctl(devHandle, I2C_SLAVE, devI2CAddr) < 0) {
		printf("Error: Couldn't find device on address!\n");
	}

	// begin transmission and request acknowledgment
	readBytes = write(devHandle, input, 1);
	if (readBytes != 1){
		printf("Error: No ACK-Bit, couldn't establish connection!");
	} else {
		// read response
		readBytes = read(devHandle, input, sizeof(float)*(*numDev));
		if (readBytes != 4){
			printf("Error: Received no data!");
		} else {
//			printf("%x\n", u.b[0]);
//			printf("%x\n", u.b[1]);
//			printf("%x\n", u.b[2]);
//			printf("%x\n", u.b[3]);
		}
	}
	// close connection and return
	close(devHandle);

//	int loop;
//	for(loop = 0; loop < sizeof(input)/sizeof(input[0]); loop++){
//		printf("%x", input[loop]);
//	}

//	input[0]=0x00;
//	input[1]=0x00;
//	input[2]=0x80;
//	input[3]=0x40;
//
//	input[4]=0x00;
//	input[5]=0x00;
//	input[6]=0x80;
//	input[7]=0x40;

//
//	u[0].b[0]=0x00;
//	u[0].b[1]=0x00;
//	u[0].b[2]=0x80;
//	u[0].b[3]=0x40;
//
//
//	u[1].b[0]=0x00;
//	u[1].b[1]=0x00;
//	u[1].b[2]=0x80;
//	u[1].b[3]=0x40;

	int counter = 0;
//
	//place input array inside union
	int i,j;
	for(i = 0; i < *numDev; i++){
		for(j = 0; j < sizeof(float); j++){
			u[i].b[j]=input[counter];
			counter++;
		}
	}

	*returnArray = (float*)malloc(sizeof(float)*(*numDev));
	memset(*returnArray, 0, sizeof(float)*(*numDev));

	for(i = 0; i < *numDev; i++){
		(*returnArray)[i]=u[i].fval;
	}

}


//free memory in heap
void cleanUp(float* returnArray){
	free(returnArray);
}
