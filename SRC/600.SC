;**
;**	Logics for room 600 -- falling out of the game
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	September 29, 1989
;**


(script#				600)

(public
	rm600				0
)


(define	vFallingOutOfTheGame		601)
	(define	lLarry				0)
	(define	lPatti				1)


(define	sThruTheRabbitHole		600)


(instance rm600 of Room
	(properties
		picture		99
	)


	(method (init)
		(HandsOff)
		(SetFlg cantSave)
		(SetFlg noCursor)
		(SetFlg preventAutoSave)
		(music
			number			sThruTheRabbitHole,
			loop				FOREVER,
			play ,			
		)

		(super init)
		(= oldGameSpeed (theGame setSpeed 3))

		(ego
			view 				vFallingOutOfTheGame,
			setLoop 			lPatti,
			setStep			22 12,
			init,
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState)

		(ego
			posn 				(Random 10 310) -40,
			setMotion		MoveTo (Random 10 310) 224 RoomScript,
		)
		(switch (= state newState)

			(2
				(Printf "\"%s I'm falling!!\"" expletive)
			)
			
			(4
				(Print "\"Larry! What happened?\"")
			)
			
			(5
				(ego setLoop lLarry)
			)
			
			(8
				(Print "\"I don't know, Patti, but it feels like we just
					fell out of the game!!")
			)

			(9
				(Print "\"The question is: where will we end up?\"")
			)

			(10
				(theGame setSpeed oldGameSpeed)
				(music	fade)
				(curRoom newRoom 610)
			)
		)
	)
)
