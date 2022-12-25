;**
;**	Logics for script 421 -- Manager script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 24, 1989
;**


(script#				421)

(public
	ManagerScript	0
)

(include "420.sh")


(enum												;**	ManagerScript state values
	intro
	knockAway
	managerOut
	whoTheHell
	ouch
	tinyDelay
	startPunching
	soLongLarry
	flyLarry
	UBdying
	allDead
)


(local
	punchCounter
	[string  40] 
	[string2 22]
)


(instance ManagerScript of Script
	(method (changeState newState)	
		(ShowState self newState 4 green)

		(switch (= state newState)

			(intro
				(Load VIEW vLdeadStageManager)
				(Load SOUND sNearDanger)
				(Load SOUND s1Boing)
				(Load SOUND sHitBottom)
			)

			(knockAway
				(Print "Ignoring the \"Authorized Personnel Only\"
					sign on the stage door, you merrily bang away.
					Probably nothing will happen..."
				)
				(someObject
					setCycle			EndLoop self,
				)
				(music
					number			sNearDanger,
					loop				FOREVER,
					play,
				)
			)

			(managerOut
				(someObject			stopUpd)
				(client
					setPri			9,
					setCycle			Forward,
					posn				33 143,
				)
				(delay 3)
			)

			(whoTheHell
				(Print "\"Who in the hell do you think you are 
					to knock on my stage door and disturb my show?\" 
					roars the burly stage manager.
					\"How dare you!\"" BIG)
				(Print "(Oh, no, Larry! What have you done now?)" LOW)
				(client
					loop				lStompingFoot,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(ouch
				(ego					hide)
				(client
					loop				lLouch,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(tinyDelay
				(Print "\"Ouch!\"" HIGH #dispose)
				(theGame setSpeed 1)
				(delay 3)
			)

			(startPunching
				(cls)
				(client
					loop				lLpunchingBag,
					cycleSpeed		(Random 0 2),
					cel				0,
					setCycle			EndLoop self,
				)
				(if (>= 10 (++ punchCounter))
					(-- state)
				)
			)

			(soLongLarry
				(client
					loop				lBigPunch,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(flyLarry
				(ego
					view				vStageManager,
					setLoop			lLflying,
					posn				60 128,
					setStep			20 9,
					setPri			11,
					setMotion		MoveTo 312 68 self,
					show,
				)
				(music
					stop,
					number			s1Boing,
					loop				1,
					play,
				)
				(client
					loop				lTalking,
					cel				0,
					stopUpd,
				)
				(curRoom 
					north 			0,
					east				0,
				)
			)

			(UBdying
				(music
					stop,
					number			sHitBottom,
					loop				1,
					play,
				)
				(client
					hide,
				)
				(someObject
					setCycle			BegLoop self,
				)
			)

			(allDead
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vLdeadStageManager,
					register 	(Format @string 
										"You know, indiscriminate knocking could well 
										be the death of you..."),
					next			(Format @string2 "That's you all over!"),
				)
				(self dispose)
			)
		)
	)
)


