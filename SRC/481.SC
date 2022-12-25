;**
;**	Logics for room 481 -- Close-up of P & L doing it
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 3, 1989
;**


(script#				481)

(include	"480.sh")


(public
	rm481				0
)


(procedure
	Print481
)


(enum												;**	RoomScript state values
	intro
	ohLarry
	wonderful
	ironic
	ironic2
	neverKnew
	gotoBlack
	climax
	removeEm
	forNothing
	changeRooms
)


(local
	[string 200]
)


(instance rm481 of Room
	(properties
		picture		485
		horizon		5
	)


	(method (init)
		(HandsOff)

		(Load PICTURE blackPicture)
		(Load VIEW vThermometer)
		(Load FONT BIG_FAT_18)
		(Load SOUND sThermometers)
		(Load SOUND sFadeToBlack2)

		(StatusLine	disable)
		(TheMenuBar	hide)

		(music
			number			sThermometers,
			loop				2,
			play,
		)

		(super init)

		(self
			setScript 		RoomScript
		)

		(aThermometerP
			setCycle			EndLoop,
			init,
		)
		(aThermometerL		
			setCycle			EndLoop,
			init,
		)

		(systemWindow
			color 			white,
			back				black,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
		(theGame setSpeed 6)
	)


	(method (handleEvent event)
		(if (and	(== (event type) keyDown)
					(== (event claimed) FALSE)
					(== (event message) `#8))
			(Print "Skipping ahead...")
			(SetFlg tookShortcut)
			(curRoom newRoom 484)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(intro
				(= cycles 33)
			)

			(ohLarry
				(Format @string "\"Oh, Larry!\"")
				(= seconds (Print481))
			)

			(wonderful
				(Format @string "\"Yes, yes!
					Oh, Larry!
					That's wonderful!!
					I didn't know pectorals could pulsate like that!\"")
				(= seconds (Print481))
			)

			(ironic
				(Format @string "You think to yourself,
					\"How ironic!
					Of all (both) the women I've known,
					this piano-player turns out to be the greatest!\"")
				(= seconds (Print481))
			)

			(ironic2
				(Format @string "Patti thinks to herself,
					\"How ironic!
					Of all the men I've had, 
					this dork turns out to be the greatest!\"")
				(= seconds (Print481))
			)

			(neverKnew
				(Format @string "Simultaneously, 
					you each murmur into the other's ear, 
					\"I never knew it could be this good!\"")
				(= seconds (Print481))
			)

			(gotoBlack
				(curRoom drawPic blackPicture IRISIN)
				(music
					number			sFadeToBlack2,
					loop				2,
					play,
				)
				(aThermometerP
					setLoop			lPulsating,
					cycleSpeed		0,
					setCycle			Forward,
				)
				(aThermometerL		
					setLoop			lPulsating,
					cycleSpeed		0,
					setCycle			Forward
				)
				(= cycles 55)
			)

			(climax
				(Print "\"YES!!\""
					#font				BIG_FAT_18
					#at 				0 90
					#width			125
					#time				3
					#dispose
				)
				(aThermometerP
					setLoop			lRising,
					setCel			LastCel,
					cycleSpeed		2,
					setCycle			BegLoop,
				)
				(aThermometerL		
					setLoop			lRising,
					setCel			LastCel,
					cycleSpeed		2,
					setCycle			BegLoop ,
				)
				(= cycles 55)
			)

			(removeEm
				(aThermometerP		dispose)
				(aThermometerL		dispose)
				(= cycles 33)
			)

			(forNothing
				(Format @string "As Patti drifts off to sleep, 
					you realize: this is one woman who deserves her nickname!")
				(= seconds (Print481))
			)

			(changeRooms
			 	(curRoom newRoom 482)
			)
		)
	)
)

(instance aThermometerP of Prop
	(properties
		view					vThermometer
;		loop					lRising
		x						181
		y						189
		cycleSpeed			12
	)
)
(instance aThermometerL of Prop
	(properties
		view					vThermometer
;		loop					lRising
		x						101
		y						54
		cycleSpeed			12
	)
)

(procedure (Print481 &tmp t )
	(Print @string
		#at 			0 70
		#width		125
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)

