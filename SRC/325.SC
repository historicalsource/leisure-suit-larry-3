;**
;**	Logics for room 325 -- Suzi's face
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				325)

(include	"face.sh")

(public
	rm325				0
)

;(define	vSuziFace					325)

(enum												;**	RoomScript state values
	intro
	smileForMe
	goodbye
	outtaHere
)


(instance rm325 of Room
	(properties
		picture		325
	)

	(method (init)
		(super init)

		(self
			setRegions		regBigFace,
			setLocales		locGirls,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		103  63)
		(Notify regBigFace EyeEast		184  60)
		(Notify regBigFace EyelidWest	106  65)
		(Notify regBigFace EyelidEast	181  65)
		(Notify regBigFace Nose			143 107)
		(Notify regBigFace Mouth		141 152)
		(HandsOff)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(intro
				(= cycles 11)
			)

			(smileForMe
				(React rSmile 20)
				(= cycles 33)
			)

			(goodbye
				(Print "Now you can see why Suzi Cheatem is known as
					the \"Queen of Torts!\"")
				(= cycles 11)
			)

			(outtaHere
				(curRoom newRoom 323)
			)
		)
	)
)

