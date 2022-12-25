;**
;**	Logics for Region 071 -- Big Face animation
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				071)

(include	"face.sh")

(procedure
	React
	PrintL
	PrintP
)

(public
	rm71				0
	React				1
	PrintL			2
	PrintP			3
)


(enum												;**	EyeScript state values
	EyeInit
	LookRight
	CrossEyes
	LookAtPlayer
	LookingAtPlayer
	LookLeft
)


(enum												;**	MouthScript state values
	MouthInit
	Oh
	Smile
	Smiling
	stopSmiling
	Frown
	Frowning
	stopFrowning
	Lick
)

(enum												;**	RegionScript state values
	RegIntro
	waitingToSpeak
	sayIt
	safety
)


(enum												;**	NoseScript state values
	NoseInit
	NoseStart
	NosePause
	NoseMiddle
	Inhale
	Exhale
)


(enum												;**	EyelidScript state values
	LidInit
	CloseLid
	OpenLid
	WinkEye
	Winking
	WinkDone
	LidOpen
	LidSquint
	LidClosed
)



(local
	takeCycles									;**	How long a "take" lasts
	[titleStr 30]
	[nameStr   5]
	[responseBuffer 300]
)


(instance rm71 of Region
	(method (init)
		(Load SCRIPT REVERSE)
		(super init)
		(HandsOff)
		(User canInput TRUE)

		(= responseBuffer 0)
		(Format @titleStr
			(switch curRoomNum
				(455	{Patti says...})
				(265	{Tawni says...})
				(395	{Bambi says...})
				(325	{Suzi says...})
				(335	{Dale says...})
				(435	{Cherri says...})
				(else	{Carlos says...})
			)
		)
		(Format @nameStr
			(switch curRoomNum
				(455	{Patti})
				(265	{Tawni})
				(395	{Bambi})
				(325	{Suzi})
				(335	{Dale})
				(435	{Cherri})
				(else	{Carlos})
			)
		)

		(= oldGameSpeed (theGame setSpeed 6))

		(if (!= curRoomNum 325)
			(music
				number			curRoomNum,
				loop				bgMusicLoops,
				play,
			)
		)

		(self
			setScript 		RegionScript
		)

		(aEyeWest 			init)
		(aEyeEast 			init)
		(aEyelidWest 		init)
		(aEyelidEast 		init)
		(aNose 				init)
		(aMouth 				init)
	)


	(method (notify whom x1 y1 )
		(switch whom
			(EyeWest 		(aEyeWest 		posn x1 y1))
			(EyeEast 		(aEyeEast 		posn x1 y1))
			(EyelidWest 	(aEyelidWest	posn x1 y1))
			(EyelidEast 	(aEyelidEast	posn x1 y1))
			(Nose 			(aNose 			posn x1 y1))
			(Mouth 			(aMouth 			posn x1 y1))
;**				(else				(Print "ERROR: in rm71 Notify."))
		)
	)


	(method (newRoom n)
		(theGame setSpeed oldGameSpeed)
		(if (!= curRoomNum 325)
			(music fade)
		)
		(super newRoom n)
	)
)


(instance RegionScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'kiss')
				(Print "How you'd love to sneak a little peck
					onto that gorgeous cheek!")
			)

			((Said 'lick')
				(Printf "%s! Just think where that tongue of yours has been!"
					egoName)
			)

			((Said 'remove/cloth, skirt, dress')
				(Print "That may be possible if you'd play the game better!")
			)

			((or	(Said 'suck, hump/me')
					(Said 'crack, crap, piss, hump, fart, tit, ass, cunnilingus'))
				(Printf "My, %s! You certainly have a charming way
					with the opposite sex." egoName)
			)

			((Said '/casino')
				(PrintL "\"Do you enjoy gambling at the casino?\"")
				(PrintP "\"Are you crazy? Nobody ever wins there!\"")
			)

			((Said '/patti, tawni, bambi, suzi, dale, cherri')
				(Printf "Where? You're talking to %s, remember?" @nameStr)
			)

			((Said 'talk')
				(Printf "Just say what you wish to say, %s." egoName)
			)

			((Said '/name')
				(PrintL "\"What's your name, baby?\"")
				(PrintP "\"You said your name, then I said my name.
					Don't you remember?\"")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(RegIntro
			)

			(waitingToSpeak
				(= cycles 22)
			)

			(sayIt
				(if responseBuffer
					(Print @responseBuffer
						#at 		-1 111
						#title	@titleStr
						#width	222
						#mode		teJustCenter
					)
					(= responseBuffer 0)
				)
				(= state 0)
			)

			(safety
			)
		)
	)
)


(instance EyeScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(EyeInit
				(delay (Random 3 9))					;**	When timer expires...
				(if (Random 0 5)						;**	...look right OR
					(= state (- LookAtPlayer 1))	;**		look at player
				)
			)

			(LookRight
				(aEyeWest	setCycle	EndLoop)
				(aEyeEast	setCycle	EndLoop self)
				(= cycles 0)
				(= state LookAtPlayer)		;**	reuse code
			)


			(CrossEyes
				(aEyeWest	setCycle	EndLoop)
				(aEyeEast	setCycle	BegLoop self)
				(= cycles 0)
				(= state LookAtPlayer)		;**	reuse code
			)


			(LookAtPlayer
				(= cycles 0)
				(aEyeWest	setCycle	CycleTo cLookAtUser
									(cond
										((> cLookAtUser (aEyeWest cel)) ctForward)
										((< cLookAtUser (aEyeWest cel)) ctReverse)))
				(aEyeEast	setCycle	CycleTo cLookAtUser
									(cond
										((> cLookAtUser (aEyeEast cel)) ctForward)
										((< cLookAtUser (aEyeEast cel)) ctReverse)) self)
			)

			(LookingAtPlayer
				(= cycles takeCycles)
			)


			(LookLeft
				(aEyeWest	setCycle	BegLoop)
				(aEyeEast	setCycle	BegLoop self)
				(= state readyToStartOver)
			)
		)
	)
)

(instance EyelidScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(LidInit
				(= cycles (Random 11 33))
			)

			(CloseLid
				(aEyelidWest setCycle EndLoop)
				(aEyelidEast setCycle EndLoop self)
			)

			(OpenLid
				(aEyelidWest setCycle BegLoop)
				(aEyelidEast setCycle BegLoop self)
				(= state readyToStartOver)
			)


			(WinkEye
				(= cycles 0)
				(aEyelidWest	setCycle	EndLoop self)
				(aEyelidEast	setCel cLidSquint)
			)

			(Winking
				(= cycles takeCycles)
			)

			(WinkDone
				(aEyelidWest	setCycle	BegLoop self)
				(= state readyToStartOver)
			)


			(LidOpen
				(aEyelidWest setCel cLidOpen)
				(aEyelidEast setCel cLidOpen)
				(= cycles takeCycles)
				(= state LidInit)
			)

			(LidSquint
				(aEyelidWest setCel cLidSquint)
				(aEyelidEast setCel cLidSquint)
				(= cycles takeCycles)
				(= state LidInit)
			)

			(LidClosed
				(aEyelidWest setCel cLidClosed)
				(aEyelidEast setCel cLidClosed)
				(= cycles takeCycles)
				(= state LidInit)
			)
		)
	)
)

(instance NoseScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(NoseInit
				(= cycles (Random 22 66))
			)

			(NoseStart
				(aNose setCycle EndLoop self)
				(= cycles 0)
			)

			(NosePause
				(= cycles (Random 10 20))
			)

			(NoseMiddle
				(aNose setCycle BegLoop self)
				(= cycles 0)
				(= state readyToStartOver)
			)


			(Inhale
				(aNose setCycle EndLoop)
				(= cycles takeCycles)
				(= state NosePause)
			)


			(Exhale
				(aNose setCycle BegLoop)
				(= cycles takeCycles)
				(= state NoseInit)
			)
		)
	)
)

(instance MouthScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(MouthInit
				(aMouth
					setLoop		lMouthSmile,
					setCel		0,
				)
				(= cycles 0)
			)



			(Oh
				(aMouth
					setLoop		lMouthOh,
					cel			0,
					setCycle		EndLoop self,
				)
				(= cycles 0)
				(= state readyToStartOver)
			)


			(Smile
				(aMouth
					setLoop		lMouthSmile,
					cel			0,
					setCycle		EndLoop self,
				)
				(= cycles 0)
			)

			(Smiling
				(= cycles takeCycles)
			)

			(stopSmiling
				(aMouth
					setCycle		BegLoop self,
				)
				(= state readyToStartOver)
			)


			(Frown
				(aMouth
					setLoop		lMouthFrown,
					cel			0,
					setCycle		EndLoop self
				)
				(= cycles 0)
			)

			(Frowning
				(= cycles 10)
			)

			(stopFrowning
				(aMouth
					setCycle		BegLoop self
				)
				(= state readyToStartOver)
			)


			(Lick
				(aMouth
					setLoop		lMouthLick,
					cel			0,
					setCycle		EndLoop self
				)
				(= cycles 0)
				(= state readyToStartOver)
			)
		)
	)
)

(instance aEyeWest of Prop
	(properties
		loop					lEyeWest
		cel					cLookLeft
	)

	(method (init)
		(self
			view				curRoomNum,
			setPri			2,
			setScript		EyeScript,
		)
		(super init)
	)
)

(instance aEyeEast of Prop
	(properties
		loop					lEyeEast
		cel					cLookLeft
	)

	(method (init)
		(self
			view				curRoomNum,
			setPri			3,
		)
		(super init)
	)
)

(instance aEyelidWest of Prop
	(properties
		loop					lLidWest
		cel					cLidOpen
	)

	(method (init)
		(self
			view				curRoomNum,
			ignoreActors,
			setPri			5,
			setScript		EyelidScript,
		)
		(super init)
	)
)

(instance aEyelidEast of Prop
	(properties
		loop					lLidEast
		cel					cLidOpen
	)

	(method (init)
		(self
			view				curRoomNum,
			ignoreActors,
			setPri			6,
		)
		(super init)
	)
)

(instance aNose of Prop
	(properties
		loop					lNose
	)

	(method (init)
		(self
			view				curRoomNum,
			cycleSpeed		2,
			setPri			9,
			setScript		NoseScript,
		)
		(super init)
	)
)

(instance aMouth of Prop
	(properties
		loop					lMouthSmile
	)

	(method (init)
		(self
			view				curRoomNum,
			cycleSpeed		1,
			setPri			10,
			setScript		MouthScript,
		)
		(super init)
	)
)


(procedure (React how howLong txt)
	(if (< argc 1)
		(= how rNormal)
	)

	(if (> argc 1)
		(= takeCycles howLong)
	else
		(= takeCycles 22)
	)

	(if (> argc 2)
		(Print @txt)
	)
	(if (== (RegionScript state) 0)
		(RegionScript changeState waitingToSpeak)
	)	
	(EyeScript changeState
		(switch how
			(rFrown		LookLeft)
			(rSilly		CrossEyes)
			(else			LookAtPlayer)
		)
	)
	(EyelidScript changeState
		(switch how
			(rLook		LidOpen)
			(rSmile		LidOpen)
			(rSexy		LidClosed)
			(rOh			LidOpen)
			(rSilly		LidOpen)
			(else			LidInit)
		)
	)
	(NoseScript changeState
		(switch how
			(rSmile		Inhale)
			(rSexy		Inhale)
			(rLove		Inhale)
			(rOh			Exhale)
			(rSilly		Exhale)
			(else			NoseInit)
		)
	)
	(MouthScript changeState
		(switch how
			(rSmile		Smile)
			(rFrown		Frown)
			(rLove		Smile)
			(rSexy		Smile)
			(rOh			Oh)
			(rSilly		Lick)
			(else			MouthInit)
		)
	)
)

(procedure (PrintL)
	(Print &rest
		#at 		-1 20
		#title	{You say...}
		#width	222
		#mode		teJustCenter
	)
)

(procedure (PrintP)
 	(Format @responseBuffer &rest)
	(if (== (RegionScript state) 0)
		(RegionScript changeState waitingToSpeak)
	)	
)

