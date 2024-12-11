ImageHider is a tool to hide pictures inside another picture without being visible (to a human).

How it works:
ImageHider uses the color information of pixels to hide a b/w image. an odd pixel on the resulting image means black, an even one white.

Usage:
1. put the image that will hold the information into the execution directory and rename it to "image.png"
2. put four images with the same resolution as the first image into the same folder and call them "rimage", "gimage", "bimage" and "aimage"
    -> first letter corresponds to the encoded color channel
    -> the program only recognises 0xffffffff. The rest will be discarded and turn to black
3. run the program
4. the program will generate:
   - "encodedimage.png" - the encoded file
   - "rdecodedimage.png" - the decoded r channel image
   - "gdecodedimage.png" - the decoded g channel image
   - "bdecodedimage.png" - the decoded b channel image
   - "adecodedimage.png" - the decoded a channel image



To-Do:
    - selection for en-/decoding
    - selection for to-be-encoded channel images
    - support for more colors (grayscale)
    - decreasing file sizes to the minimum needed to store all information
